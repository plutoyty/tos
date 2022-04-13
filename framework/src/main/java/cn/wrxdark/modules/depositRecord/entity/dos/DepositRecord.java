package cn.wrxdark.modules.depositRecord.entity.dos;

import cn.wrxdark.common.entity.dos.BaseDo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 *
 * @author 刘宇阳
 * @since 2022-03-27
 */
@Data
@ToString(callSuper = true)
@TableName("deposit_record")
@AllArgsConstructor
@ApiModel(value = "DepositRecord对象", description = "存款记录")
public class DepositRecord extends BaseDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("流水号")
    @TableField("serial_number")
    @JsonProperty("serial_number")
    private String serialNumber;

    @ApiModelProperty("用户id")
    @TableField("member_id")
    @JsonProperty("member_id")
    private String memberId;

    @ApiModelProperty("产品id")
    @TableField("goods_id")
    @JsonProperty("goods_id")
    private String goodsId;


    @ApiModelProperty("活动id")
    @TableField("activity_id")
    @JsonProperty("activity_id")
    private String activityId;

    @ApiModelProperty("存款金额")
    @TableField("deposit")
    private Double deposit;

    @ApiModelProperty("状态  1 成功 0 失败")
    @TableField("status")
    private Integer status;
}
