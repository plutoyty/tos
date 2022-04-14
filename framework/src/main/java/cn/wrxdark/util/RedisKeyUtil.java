package cn.wrxdark.util;

import cn.wrxdark.cache.CachePrefix;
import cn.wrxdark.modules.verification.entity.enums.VerificationEnums;

/**
 * @author 刘宇阳
 * @create 2022/4/13
 * @description 生成redis的key的工具类
 */
public class RedisKeyUtil {
    /**
     * @description 生成已经购买的记录的key
     * @author 刘宇阳
     * @param memberId
     * @param goodsId
     * @param activityId
     * @return
     */
    public static String generateHaveBoughtKey(String memberId,String goodsId,String activityId){
        return CachePrefix.HAVE_BOUGHT.getPrefix()+"-"+memberId+"-"+goodsId+"-"+activityId;
    }

    /**
     * @description 生成有无秒杀资格的key
     * @author 刘宇阳
     * @param memberId
     * @param activityId
     * @return
     */
    public static String generateQualificationKey(String memberId,String activityId){
        return CachePrefix.NO_QUALIFICATION.getPrefix()+"-"+activityId+"-"+memberId;
    }

    /**
     * @description 生成库存的key
     * @author 刘宇阳
     * @param activityId
     * @param goodsId
     * @return
     */
    public static String generateStockKey(String goodsId,String activityId){
        return CachePrefix.GOODS_STOCK.getPrefix()+"-"+goodsId+"-"+activityId;
    }

    /**
     * @description 生成活动的key
     * @author 刘宇阳
     * @param activityId
     * @return
     */
    public static String generateActivityKey(String activityId){
        return CachePrefix.ACTIVITY.getPrefix()+"-"+activityId;
    }

    /**
     * @description 生成活动商品的key
     * @author 刘宇阳
     * @param goodsId
     * @return
     */
    public static String generateGoodsKey(String goodsId,String activityId){
        return CachePrefix.GOODS.getPrefix()+"-"+activityId+"-"+goodsId;
    }

    /**
     * @description 生成用户的key
     * @author 刘宇阳
     * @param memberId
     * @return
     */
    public static String generateMemberKey(String memberId){
        return CachePrefix.MEMBER+memberId;
    }

    /**
     * @description 生成秒杀临时路径的key
     * @author 刘宇阳
     * @param activityId
     * @param goodsId
     * @param memberId
     * @return
     */
    public static String generateTempPathKey(String memberId,String goodsId,String activityId){
        return CachePrefix.TEMP_PATH+"-"+memberId+"-"+goodsId+"-"+activityId;
    }

    /**
     * 生成验证码缓存key
     * @param email 邮箱
     * @param uuid 客户端uuid
     * @param type  验证类型
     * @return 验证码缓存key
     */
    public static String  generateEmailKey(String email, String uuid, VerificationEnums type){
        return CachePrefix.EMAIL_CODE.getPrefix() + type.name() + uuid + email;
    }

    /**
     * 活动规则
     * @param id
     * @return
     */
    public static String generateRuleKey(String id) {
        return CachePrefix.ACTIVITY_RULE+"_"+id;
    }
}
