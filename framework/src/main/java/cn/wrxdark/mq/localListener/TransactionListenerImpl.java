package cn.wrxdark.mq.localListener;

import cn.wrxdark.cache.Cache;
import cn.wrxdark.common.entity.enums.StatusEnum;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.modules.depositRecord.entity.dos.DepositRecord;
import cn.wrxdark.modules.depositRecord.mapper.DRecordMapper;
import cn.wrxdark.modules.member.mapper.MemberMapper;
import cn.wrxdark.modules.stockLog.entity.dos.StockLog;
import cn.wrxdark.modules.stockLog.mapper.StockLogMapper;
import cn.wrxdark.mq.entity.ProducerArg;
import cn.wrxdark.util.RedisKeyUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘宇阳
 * @create 2022/3/28
 * @description producer端执行本地事务和监听回查操作
 */
@Slf4j
@RocketMQTransactionListener()
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Autowired
    private StockLogMapper stockLogMapper;
    @Autowired
    private Cache cache;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private DRecordMapper recordMapper;
    /**
     * 执行本地事务(在前面已经执行完了)
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.println(msg);
        System.out.println(arg);
        try{
            ProducerArg producerArg= (ProducerArg) arg;
            String goodsId= producerArg.getGoodsId();
            String memberId= producerArg.getMemberId();
            String activityId= producerArg.getActivityId();
            String stockLogId=producerArg.getStockLogId();
            //扣减redis中的库存
            decrStock(goodsId,activityId);
            log.info("扣减redis中的库存");
            //扣减用户余额
            String goodsKey= RedisKeyUtil.generateGoodsKey(goodsId);
            double goodsPrice= (double) cache.getHash(goodsKey,"initialDeposit");
            memberMapper.decrBalance(memberId,goodsPrice);
            log.info("扣减了用户余额");
            //在redis中存下用户已购买的记录
            String haveBoughtKey=RedisKeyUtil.generateHaveBoughtKey(memberId,goodsId,activityId);
            String activityKey=RedisKeyUtil.generateActivityKey(activityId);
            LocalDateTime endTime= (LocalDateTime) cache.getHash(activityKey,"endTime");
            Duration duration= Duration.between(LocalDateTime.now(),endTime );
            //活动结束即超时删除
            long expireSeconds=duration.getSeconds();
            log.info("保存下该用户的已经购买过的记录");
            cache.put(haveBoughtKey,"",expireSeconds, TimeUnit.SECONDS);
            //更新流水状态为提交
            stockLogMapper.updateStatus(stockLogId,StatusEnum.STOCK_LOG_COMMIT.statusCode());
            log.info("更新流水状态为提交");
            //保存存款记录
            DepositRecord dr=new DepositRecord(UUID.randomUUID().toString(),memberId,goodsId,activityId,goodsPrice,StatusEnum.BUY_DEPOSIT_SUCESS.statusCode());
            recordMapper.insert(dr);
            return RocketMQLocalTransactionState.COMMIT;
        }catch (ServiceException e){
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 如果出现网络问题，则broker将不会收到COMMIT类型的消息
     * 就会定时回查，会进入这个方法
     * @param msg
     * @return
     */
    @SneakyThrows
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("============== 查询流水记录{}的状态==========",msg);
        String json = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSON.parseObject(json);
        String stockLogId = jsonObject.getString("stockLogId");
        log.info("回查的流水记录id为{}",stockLogId);
        StockLog stockLogInSql = stockLogMapper.selectById(stockLogId);
        if(stockLogInSql.getStatus().equals(StatusEnum.STOCK_LOG_INIT.statusCode())){
            //没有处理该流水，未知状态
            return RocketMQLocalTransactionState.UNKNOWN;
        }else if(stockLogInSql.getStatus().equals(StatusEnum.STOCK_LOG_COMMIT.statusCode())){
            //流水已经处理了
            return RocketMQLocalTransactionState.COMMIT;
        }else{
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * @description 查询以及扣减库存，同步锁保证线程安全
     * @param goodsId 商品id
     * @param activityId 活动id
     * @return
     */
    private synchronized boolean decrStock(String goodsId,String activityId) {
        String key= RedisKeyUtil.generateStockKey(goodsId,activityId);
        int restStock= (int) cache.get(key);
        //库存不足
        if(restStock==0){
            return false;
        }
        //扣减缓存中的库存
        cache.decrData(key,1);
        return true;
    }
}