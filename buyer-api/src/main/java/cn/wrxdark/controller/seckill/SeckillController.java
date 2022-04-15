package cn.wrxdark.controller.seckill;

import cn.wrxdark.cache.Cache;
import cn.wrxdark.cache.annotations.AccessLimit;
import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.modules.seckill.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘宇阳
 * @create 2022/4/13
 * @description
 */
@RestController
@RequestMapping("/seckill")
@Slf4j
@Api(tags = "秒杀模块接口")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private Cache cache;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DefaultRedisScript stockLua;

    /**
     * @description 创建临时秒杀地址，内部含一些校验
     * @author 刘宇阳
     * @create 2022/4/12 10:08
     */
    @Operation(summary = "创建临时秒杀地址，内部含一些校验")
    @PostMapping("/path/{memberId}/{activityId}/{goodsId}")
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = false)
    public ResultMessage<String> getSeckillTempPath(
            @NotNull(message = "请求参数不足") @PathVariable("memberId") String memberId,
            @NotNull(message = "请求参数不足") @PathVariable("activityId") String activityId,
            @NotNull(message = "请求参数不足") @PathVariable("goodsId") String goodsId
    ) {
        String path=seckillService.getPath(memberId,goodsId,activityId);
        return ResultUtil.data(path);
    }

    /**
     * @description 检查临时路径是否有问题，没问题就支付
     * @author 刘宇阳
     * @param memberId 用户id
     * @param activityId 活动id
     * @param goodsId 商品id
     * @param path 临时的秒杀路径
     * @return
     */
    @Operation(summary = "检查临时路径是否有问题，没问题就支付")
    @PostMapping("/check-and-kill/{memberId}/{activityId}/{goodsId}/{path}")
    public ResultMessage checkSeckillTempPath(
            @NotNull(message = "请求参数不足") @PathVariable("memberId") String memberId,
            @NotNull(message = "请求参数不足") @PathVariable("activityId") String activityId,
            @NotNull(message = "请求参数不足") @PathVariable("goodsId") String goodsId,
            @NotNull(message = "请求参数不足") @PathVariable("path") String path
    ){
        seckillService.check(memberId,goodsId,activityId,path);
        //没抛异常，说明成功，开始支付流程
        log.info("路径正确");
        seckillService.pay(memberId,goodsId,activityId);
        return ResultUtil.success();
    }

    /**
     * 用于测试的直接秒杀接口
     * @param memberId 用户id
     * @param activityId 活动id
     * @param goodsId 商品id
     * @return
     */
    @PostMapping("/seckill-for-test/{memberId}/{activityId}/{goodsId}")
    public ResultMessage checkSeckillTempPath(
            @NotNull(message = "请求参数不足") @PathVariable("memberId") String memberId,
            @NotNull(message = "请求参数不足") @PathVariable("activityId") String activityId,
            @NotNull(message = "请求参数不足") @PathVariable("goodsId") String goodsId
    ){
        seckillService.pay(memberId,goodsId,activityId);
        return ResultUtil.success();
    }



    @GetMapping("/test")
    public void test(){
        String key = "stock";
        List<String> keys = new ArrayList<>();
        keys.add(key);
        Long execute = (Long) redisTemplate.execute(stockLua,keys);
        if(execute == 0){
            throw new ServiceException(ResultCode.GOODS_SKU_QUANTITY_ERROR);
        }
    }

    /**
     * @description 弃用 数学公式验证码
     * @author 刘宇阳
     * @param memberId
     * @param goodsId
     * @param activityId
     * @return
     */
    @GetMapping("/formula-image")
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = false)
    public ResultMessage<String> getFormulaImage(
            @NotNull(message = "请求参数不足") @RequestParam("user_id") String memberId,
            @NotNull(message = "请求参数不足") @RequestParam("goods_id") String goodsId,
            @NotNull(message = "请求参数不足") @RequestParam("activity_id") String activityId
    ) {
        return seckillService.getSeckillFoumulaImage( memberId, goodsId, activityId);
    }
}