package cn.wrxdark.cache;

import cn.wrxdark.common.security.enums.UserEnums;

/**
 * 缓存前缀
 *
 */
public enum CachePrefix {
    /**
     * nonce
     */
    NONCE,
    /**
     * 在线会员统计
     */
    ONLINE_MEMBER,

    /**
     * token 信息
     */
    ACCESS_TOKEN,
    /**
     * token 信息
     */
    REFRESH_TOKEN,
    /**
     * 权限
     */
    PERMISSION_LIST,
    /**
     * 系统设置
     */
    SETTING,

    /**
     * 验证码滑块源
     */
    VERIFICATION,

    /**
     * 验证码滑块源
     */
    VERIFICATION_IMAGE,

    /**
     * 快递平台
     */
    EXPRESS,

    /**
     * 图片验证码
     */
    CAPTCHA,

    /**
     * 商品
     */
    GOODS,

    /**
     * 商品SKU
     */
    GOODS_SKU,

    /**
     * 商品sku
     */
    SKU,
    /**
     * sku库存
     */
    SKU_STOCK,
    /**
     * 商品库存
     */
    GOODS_STOCK,

    /**
     * 商品分类 树状结构
     */
    CATEGORY,
    /**
     * 商品分类 集合
     */
    CATEGORY_ARRAY,
    /**
     * 浏览次数
     */
    VISIT_COUNT,
    /**
     * 存储方案
     */
    UPLOADER,
    /**
     * 地区
     */
    REGION,
    /**
     * 系统设置
     */
    SETTINGS,
    /**
     * 邮箱验证码
     */
    EMAIL_CODE,
    /**
     * 管理员角色权限对照表
     */
    ADMIN_URL_ROLE,

    /**
     * 店铺管理员角色权限对照表
     */
    STORE_URL_ROLE,
    /**
     * 邮箱验证标识
     */
    EMAIL_VALIDATE,
    /**
     * 交易
     */
    TRADE,
    /**
     * 验证码key
     */
    VERIFICATION_KEY,
    /**
     * 验证码验证结果
     */
    VERIFICATION_RESULT,
    /**
     * 订单暂时缓存
     */
    ORDER,
    /**
     * 敏感词
     */
    SENSITIVE,
    /**
     * 已购买记录
     */
    HAVE_BOUGHT,
    /**
     * 活动
     */
    ACTIVITY,
    /**
     * 库存流水
     */
    STOCK_LOG,
    /**
     * 接口访问限制
     */
    API_ACCESS_LIMIT,
    /**
     * 秒杀接口临时路径
     */
    TEMP_PATH,
    /**
     * 没有秒杀资格
     */
    NO_QUALIFICATION,
    /**
     * 用户
     */
    MEMBER;

    public static String removePrefix(String str) {
        return str.substring(str.lastIndexOf("}_") + 2);
    }

    /**
     * 通用获取缓存key值
     *
     * @return 缓存key值
     */
    public String getPrefix() {
        return "{" + this.name() + "}_";
    }

    /**
     * 获取缓存key值 + 用户端
     * 例如：三端都有用户体系，需要分别登录，如果用户名一致，则redis中的权限可能会冲突出错
     *
     * @param user 角色
     * @return 缓存key值 + 用户端
     */
    public String getPrefix(UserEnums user) {
        return "{" + this.name() + "_" + user.name() + "}_";
    }
}
