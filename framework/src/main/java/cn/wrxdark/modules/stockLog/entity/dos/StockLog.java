package cn.wrxdark.modules.stockLog.entity.dos;

import cn.wrxdark.common.entity.dos.BaseDo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘宇阳
 * @create 2022/3/28
 * @description 库存流水，用于消息队列的
 */
@Data
@AllArgsConstructor
@TableName("stock_log")
@ApiModel(value = "StockLog对象", description = "")
public class StockLog extends BaseDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    @TableField("goods_id")
    private String goodsId;

    @ApiModelProperty("活动id")
    @TableField("activity_id")
    private String activityId;

    @ApiModelProperty("记录本次流水操作多少库存")
    @TableField("amount")
    private Integer amount;

    @ApiModelProperty("1为初始状态，2表示下单扣减成功，3表示回滚")
    @TableField("status")
    private Integer status;
}
