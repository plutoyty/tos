package cn.wrxdark.modules.seckill;

import cn.wrxdark.common.entity.vo.ResultMessage;

/**
 * @author 刘宇阳
 * @create 2022/4/13
 * @description
 */
public interface SeckillService {
    /**
     * 生成数学公式验证码的图片(弃用)
     * @param memberId
     * @param goodsId
     * @param activityId
     * @return
     */
    @Deprecated
    ResultMessage<String> getSeckillFoumulaImage(
            String memberId,
            String goodsId,
            String activityId
    );

    /**
     * @author 刘宇阳
     * @description 检验验证码与验证资格，成功则返回临时路径，同时路径也被存到redis内
     * @param memberId 用户id
     * @param goodsId 商品id
     * @param activityId 活动id
     * @return
     */
    String getPath(String memberId,String goodsId,String activityId);

    /**
     * @author 刘宇阳
     * @description 验证秒杀地址
     * @create 2022/4/12 10:05
     * @param activityId 活动id
     * @param goodsId 商品id
     * @param memberId 用户id
     * @return
     */
    void check(String memberId, String goodsId,String activityId, String path);

    /**
     * @description 判断用户余额、库存后，扣钱、扣减redis内的库存、生成库存流水、发送事务型消息去异步扣减数据库内库存
     * @param memberId 用户id
     * @param goodsId 商品id
     * @param activityId 活动id
     */
    void pay(String memberId,String goodsId,String activityId);
}
