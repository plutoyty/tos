package cn.wrxdark.common.entity.enums;

import io.swagger.models.auth.In;

/**
 * @author 20级三班刘宇阳
 * @create 2022/3/24
 */
public enum StatusEnum {
    //订单未被支付
    ORDER_NOT_BEEN_PAID(0),
    //订单已支付
    ORDER_HAVE_BEEN_PAID(1),
    //订单过期
    ORDER_PAST_DUE(2),
    //活动已结束
    ACTIVITY_HAVE_ENDED(0),
    //购买成功
    BUY_DEPOSIT_SUCESS(1),
    //购买失败
    BUY_DEPOSIT_FAIL(0),
    //库存流水初始状态
    STOCK_LOG_INIT(0),
    //库存流水初始状态
    STOCK_LOG_SUCCESS(1),
    GOODS_ON_SHELF(1),
    ACTIVITY_OPEN(1);

    private final Integer statusCode;

    StatusEnum(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer statusCode() {
        return statusCode;
    }
}
