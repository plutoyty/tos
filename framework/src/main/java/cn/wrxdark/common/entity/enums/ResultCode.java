package cn.wrxdark.common.entity.enums;

/**
 * 返回状态码
 *
 */
public enum ResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(200, "成功"),

    /**
     * 失败返回码
     */
    ERROR(400, "服务器繁忙，请稍后重试"),

    /**
     * 参数异常
     */
    PARAMS_ERROR(4002, "参数异常"),


    /**
     * 系统异常
     */
    LIMIT_ERROR(1003, "访问过于频繁，请稍后再试"),
    /**
     * 分类
     */
    CATEGORY_NOT_EXIST(10001, "商品分类不存在"),
    CATEGORY_PARENT_NOT_EXIST(10003, "该分类名称已存在"),
    CATEGORY_BEYOND_THREE(10004, "最多为三级分类,添加失败"),
    CATEGORY_SAVE_ERROR(10007, "分类保存失败"),

    /**
     * 商品
     */
    GOODS_ERROR(11001, "商品异常，请稍后重试"),
    GOODS_NOT_EXIST(11001, "商品已下架"),
    GOODS_UNDER_ERROR(11003, "商品下架失败"),
    GOODS_UPPER_ERROR(11004, "商品上架失败"),
    GOODS_AUTH_ERROR(11005, "商品审核失败"),
    GOODS_SKU_SN_ERROR(11007, "商品SKU货号不能为空"),
    GOODS_SKU_PRICE_ERROR(11008, "商品SKU价格不能小于等于0"),
    GOODS_SKU_COST_ERROR(11009, "商品SKU成本价不能小于等于0"),
    GOODS_SKU_WEIGHT_ERROR(11010, "商品重量不能为负数"),
    GOODS_SKU_QUANTITY_ERROR(11011, "商品库存数量不能为负数"),
    GOODS_SKU_QUANTITY_NOT_ENOUGH(11011, "商品库存不足"),
    MUST_HAVE_GOODS_SKU(11012, "规格必须要有一个！"),
    GOODS_NOT_BELONG_TO_ACTIVITY(11013,"本商品不属于该秒杀活动"),
    GOODS_REPEAT_BUY_ERROR(11014,"秒杀商品不能重复购买"),
    GOODS_BUY_AMOUNT_LIMIT_ERROR(11015,"购买数量不能大于限购量"),

    /**
     * 规格
     */
    SPEC_SAVE_ERROR(13001, "规格修改失败"),
    SPEC_UPDATE_ERROR(13002, "规格修改失败"),
    SPEC_DELETE_ERROR(13003, "分类已经绑定此规格，请先解除关联"),

    /**
     * 品牌
     */
    BRAND_SAVE_ERROR(14001, "品牌添加失败"),
    BRAND_UPDATE_ERROR(14002, "品牌修改失败"),
    BRAND_DISABLE_ERROR(14003, "品牌禁用失败"),

    /**
     * 用户
     */
    USER_EDIT_SUCCESS(20001, "用户修改成功"),
    USER_NOT_EXIST(20002, "用户不存在"),
    USER_NOT_LOGIN(20003, "用户未登录"),
    USER_AUTH_EXPIRED(20004, "用户已退出，请重新登录"),
    USER_AUTHORITY_ERROR(20005, "权限不足"),
    USER_NAME_EXIST(20007, "该用户名已被注册"),
    USER_EMAIL_EXIST(20008, "该邮箱已被注册"),
    USER_EMAIL_NOT_EXIST(20009, "邮箱不存在"),
    USER_PASSWORD_ERROR(20010, "密码不正确"),
    USER_EDIT_ERROR(20015, "用户修改失败"),
    USER_OLD_PASSWORD_ERROR(20016, "旧密码不正确"),
    USER_COLLECTION_EXIST(20017, "无法重复收藏"),
    USER_BALANCE_ERROR(20018,"用户余额不足"),

    /**
     * 购物车
     */
    CART_ERROR(30001, "读取结算页的购物车异常"),
    SHIPPING_NOT_APPLY(30005, "购物商品不支持当前收货地址配送"),

    /**
     * 订单
     */
    ORDER_NOT_EXIST(31002, "订单不存在"),
    MEMBER_ADDRESS_NOT_EXIST(31009, "订单无收货地址，请先配置收货地址"),
    ORDER_CAN_NOT_CANCEL(31012, "当前订单状态不可取消"),
    ORDER_HAVE_BEEN_PAID(31013,"当前订单已被支付过了"),
    CREATE_ORDER_ERROR(31014,"生成订单失败"),

    /**
     * 活动
     */
    ACTIVITY_NOT_EXIST(32001,"活动不存在"),
    ACTIVITY_NOT_START(32002,"活动未开始"),
    ACTIVITY_HAVE_ENDED(32003,"活动已结束"),
    ACTIVITY_START(32004,"活动已开启"),
    ACTIVITY_STOP(32005,"活动已停止"),
    ACTIVITY_QUALIFICATION_ERROR(32006,"没有参与秒杀活动的资格"),
    ACTIVITY_DURATION_ERROR(32007,"活动结束时间不能早于开始时间"),


    /**
     * 评价
     */
    EVALUATION_DOUBLE_ERROR(35001, "无法重复提交评价"),

    /**
     * 店铺
     */

    STORE_NOT_EXIST(50001, "此店铺不存在"),
    STORE_NOT_OPEN(50004, "该会员未开通店铺"),
    STORE_NOT_LOGIN_ERROR(50005, "未登录店铺"),
    STORE_CLOSE_ERROR(50006, "店铺关闭，请联系管理员"),


    /**
     * OSS
     */
    OSS_EXCEPTION_ERROR(80102, "文件上传失败，请稍后重试"),
    IMAGE_FILE_EXT_ERROR(1005, "不支持图片格式"),
    FILE_TYPE_NOT_SUPPORT(1010, "不支持上传的文件类型！"),

    /**
     * 验证码
     */
    VERIFICATION_SEND_SUCCESS(80201, "邮箱验证码,发送成功"),
    VERIFICATION_NOT_EXIST(80201, "验证码不存在"),
    VERIFICATION_EMAIL_CHECKED_ERROR(80210, "邮箱验证码错误，请重新校验"),
    VERIFICATION_IMAGE_CREATE_ERROR(80211,"数学公式验证码创建错误"),
    VERIFICATION_ERROR(80211,"验证码错误"),
    /**
     * 规则
     */
    RULE_NOT_COMPLETE(80311,"规则不完整"),
    RULE_ID_NULL(80312,"规则ID为空"),

    /**
     * 其他
     */
    PRICE_NEGATIVE_ERROR(90006, "价格不能是负数"),
    API_NOT_IMPLEMENT(90007,"接口未实现"),
    PATH_ERROR(90008,"路径错误");


    private final Integer code;
    private final String message;


    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
