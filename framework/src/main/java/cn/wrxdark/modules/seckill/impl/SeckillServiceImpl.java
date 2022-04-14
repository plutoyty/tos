package cn.wrxdark.modules.seckill.impl;

import cn.wrxdark.cache.Cache;
import cn.wrxdark.cache.CachePrefix;
import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.enums.StatusEnum;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.modules.member.mapper.MemberMapper;
import cn.wrxdark.modules.seckill.SeckillService;
import cn.wrxdark.modules.stockLog.entity.dos.StockLog;
import cn.wrxdark.modules.stockLog.mapper.StockLogMapper;
import cn.wrxdark.mq.entity.ConsumerArg;
import cn.wrxdark.mq.entity.ProducerArg;
import cn.wrxdark.mq.producer.TransactionProducer;
import cn.wrxdark.util.Base64Util;
import cn.wrxdark.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘宇阳
 * @create 2022/4/13
 * @description
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private Cache cache;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private TransactionProducer producer;
    @Autowired
    private StockLogMapper stockLogMapper;

    /**
     * @author 刘宇阳
     * @description 检验验证码与验证资格，成功则返回临时路径，同时路径也被存到redis内
     * @param memberId 用户id
     * @param goodsId 商品id
     * @param activityId 活动id
     * @return
     */
    @Override
    public String getPath(String memberId, String goodsId, String activityId) {
        //开始验证
        //1、是否有资格
        String qualificationKey= RedisKeyUtil.generateQualificationKey(memberId,activityId);
        //redis存的是没有资格的
        if(cache.get(qualificationKey)!=null){
            //无资格
            throw new ServiceException(ResultCode.ACTIVITY_QUALIFICATION_ERROR);
        }
        //2、是否已经购买过
        String haveBoughtKey= RedisKeyUtil.generateHaveBoughtKey(memberId, goodsId, activityId);
        if(cache.get(haveBoughtKey)!=null){
            //已经购买过了
            throw new ServiceException(ResultCode.GOODS_REPEAT_BUY_ERROR);
        }
        //3.1、活动是否存在
        String activityKey=RedisKeyUtil.generateActivityKey(activityId);
        if(cache.get(activityKey)==null){
            //活动不存在
            //TODO 活动结束或者强行中断活动都会导致不存在，是否需要修改这里的逻辑
            throw new ServiceException(ResultCode.ACTIVITY_NOT_EXIST);
        }
        //3.2、活动是否开始了
        LocalDateTime startTime= (LocalDateTime) cache.getHash(activityKey, "startTime");
        if(LocalDateTime.now().isBefore(startTime)){
            //活动尚未开始
            throw new ServiceException(ResultCode.ACTIVITY_NOT_START);
        }
        //4、商品是否是秒杀商品
        String goodsKey=RedisKeyUtil.generateGoodsKey(goodsId);
        if(cache.get(goodsKey)==null){
            //商品不存在
            throw new ServiceException(ResultCode.ACTIVITY_NOT_EXIST);
        }
        //通过检测，可以返回访问路径，允许秒杀
        String path = UUID.randomUUID().toString();
        String pathKey=RedisKeyUtil.generateTempPathKey(memberId,goodsId,activityId);
        cache.put(pathKey,path);
        log.info("创建了临时秒杀路径");
        return path;
    }

    /**
     * @author 刘宇阳
     * @description 验证秒杀地址
     * @create 2022/4/12 10:05
     * @param activityId 活动id
     * @param goodsId 商品id
     * @param memberId 用户id
     * @return
     */
    @Override
    public void check(String path, String memberId, String goodsId, String activityId) {
        String key=RedisKeyUtil.generateTempPathKey(memberId,goodsId,activityId);
        String pathInRedis= (String) cache.get(key);
        boolean res=path.equals(pathInRedis);
        //路径不正确的
        if(!res){
            throw new ServiceException(ResultCode.PATH_ERROR);
        }
    }

    /**
     * @description 判断用户余额、库存后，扣钱、扣减redis内的库存、生成库存流水、发送事务型消息去异步扣减数据库内库存
     * @param memberId 用户id
     * @param goodsId 商品id
     * @param activityId 活动id
     */
    @Override
    public void pay(String memberId, String goodsId, String activityId) {
        log.info("生成库存流水");
        StockLog stockLog=new StockLog(goodsId,activityId,1, StatusEnum.STOCK_LOG_INIT.statusCode());
        stockLogMapper.insert(stockLog);
        ProducerArg producerArg=new ProducerArg(stockLog.getId(),memberId,goodsId,activityId);
        ConsumerArg payload=new ConsumerArg(goodsId,activityId,stockLog.getId());
        Message<ConsumerArg> message = MessageBuilder.withPayload(payload).build();
        //发送事务消息
        boolean sendSuccess=producer.produce(message,producerArg);
        log.info("发送事务型消息");
        if(!sendSuccess){
            throw new ServiceException(ResultCode.CREATE_ORDER_ERROR);
        }
    }
//    ------------------------------验证码部分，已废弃------------------------------------------------------

    /**
     *
     * @author 刘宇阳
     * @description 获得数学公式验证码的图片(已弃用)
     * @create 2022/4/11
     * @param memberId 用户id
     * @param goodsId 商品id
     * @param activityId 活动id
     * @return
     */
    @Override
    public ResultMessage<String> getSeckillFoumulaImage(
            String memberId,
            String goodsId,
            String activityId
    ) {
        try {
            int width = 100;
            int height = 32;
            //创建图像
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            // 设置背景颜色
            g.setColor(new Color(0xFF3042));
            g.fillRect(0,0,width-1,height-1); //背景颜色填充
            //画边框
            g.setColor(Color.WHITE);
            g.drawRect(0,0,width-1,height-1);
            //随机
            Random random = new Random();
            //100个干扰点
            int noiseDotNum=100;
            for (int i = 0; i < noiseDotNum; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                g.drawOval(x,y,0,0);
            }
//            10个干扰线
            int noiseLine=100;
            for(int i=0;i<noiseLine;i++){
                int x1 =getIntRandom(0,(int)(width*0.6));
                int y1 =getIntRandom(0,(int)(height*0.6));
                int x2 =getIntRandom((int)(width*0.4),width);
                int y2 =getIntRandom((int)(height*0.2),height);
                int R=(int) (Math.random()*255);
                int G=(int) (Math.random()*255);
                int B=(int) (Math.random()*255);
                int alpha=(int) (Math.random()*255);
                g.setColor(new Color(R,G,B,alpha));
                g.drawLine(x1, y1, x2, y2);
            }
            //创建随机码
            String fomula = generateFomula(random);
            //把验证码计算结果存到redis中(1分钟)
            int res = calc(fomula);
            cache.put(generateVerifyCodeKey(memberId,goodsId,activityId),res,2L, TimeUnit.MINUTES);
            //将公式画在图片上
            fomula+="=?";
            g.setColor(new Color(255, 255, 255)); //设颜色
            g.setFont(new Font("Candara", Font.BOLD,24)); //设字体
            g.drawString(fomula, 12, 24);
            g.dispose();
            //将图片输出
            String base64Image= Base64Util.bufferedImageToBase64(image);
            log.info("创建了数学公式图");
            return ResultUtil.data(base64Image);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.VERIFICATION_IMAGE_CREATE_ERROR);
        }
    }


    //验证码为3个数字（0~9）做两次运算，运算符有加、减、乘
    private static char[] opts = new char[]{'+','-','*'};

    /**
     * @author 刘宇阳
     * @create 2022/4/11
     * @description 创建公式
     * @param rdm
     * @return
     */
    private String generateFomula(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = opts[rdm.nextInt(3)];
        char op2 = opts[rdm.nextInt(3)];
        String exp = "" + num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    /**
     * @author 刘宇阳
     * @create 2022/4/11
     * @description 使用ScriptEngine计算验证码
     * @param exp 表达式
     * @return 公式结果
     */
    private int calc(String exp) {
        try {
            //表达式引擎
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @author 刘宇阳
     * @create 2022/4/12 09:46
     * @description 创建验证码放进redis里时使用的key
     * @param userId
     * @param goodsId
     * @param activityId
     * @return
     */
    private String generateVerifyCodeKey(String userId, String goodsId, String activityId){
        StringBuilder sb=new StringBuilder();
        sb.append(CachePrefix.VERIFICATION_KEY).append("-")
                .append(activityId).append("-")
                .append(goodsId).append("-")
                .append(userId);
        return sb.toString();
    }

    /***
     * @return 随机返一个指定区间的数字
     */
    private int getIntRandom(int start,int end) {
        if(end<start) {
            int t=end;
            end=start;
            start=t;
        }
        int i=start+(int) (Math.random()*(end-start));
        return i;
    }
}
