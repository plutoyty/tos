package cn.wrxdark.modules.goods.entity.dos;

import cn.wrxdark.common.entity.dos.BaseDo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 刘宇阳
 * @since 2022-03-23
 * @description 商品
 */
@Data
@ToString(callSuper = true)
@TableName("tos_goods")
@ApiModel(value = "Goods对象", description = "产品")
public class Goods extends BaseDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("期限(以月为单位)")
    @TableField("term")
    private Integer term;

    @ApiModelProperty("年利率(不以百分数而是以小数)")
    @TableField("annual_profit")
    @JsonProperty("annual_profit")
    private Double annualProfit;

    @ApiModelProperty("起存金额")
    @TableField("initial_deposit")
    @JsonProperty("initial_deposit")
    private Double initialDeposit;

    @ApiModelProperty("递增金额")
    @TableField("incremental_deposit")
    @JsonProperty("incremental_deposit")
    private Double incrementalDeposit;

    @ApiModelProperty("起息日")
    @TableField("value_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonProperty("value_date")
    private Date valueDate;

    @ApiModelProperty("单人限额")
    @TableField("person_limit")
    @JsonProperty("person_limit")
    private Double personLimit;

    @ApiModelProperty("单日限额")
    @TableField("day_limit")
    @JsonProperty("day_limit")
    private Double dayLimit;

    @ApiModelProperty("结息方式(1是定期，2是)")
    @TableField("settlement_way")
    @JsonProperty("settlement_way")
    private Integer settlementWay;

    @ApiModelProperty("到期日")
    @TableField("due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonProperty("due_date")
    private Date dueDate;

    @ApiModelProperty("风险等级")
    @TableField("risk_level")
    @JsonProperty("risk_level")
    private Integer riskLevel;

    @ApiModelProperty("库存")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty("状态(0 下架，1 上架)")
    @TableField("`status`")
    private Integer status;
}
