package cn.wrxdark.modules.activity.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 刘宇阳
 * @create 2022/4/14
 * @description 活动+规则
 */
@Data
@ToString
@AllArgsConstructor
public class ActivityRuleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品id")
    @TableField("goods_id")
    @JsonProperty("goods_id")
    private String goodsId;

    @ApiModelProperty("活动名称")
    @TableField("`activity_name`")
    private String activityName;

    //    @ApiModelProperty("封面图片地址")
    @TableField("cover_image")
    @JsonProperty("cover_image")
    private String coverImage;

    @ApiModelProperty("活动描述")
    @TableField("`describe`")
    private String describe;

    @ApiModelProperty("活动产品数量")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty("活动开始时间")
    @TableField("start_time")
    @JsonProperty("start_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty("活动结束时间")
    @TableField("end_time")
    @JsonProperty("end_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("规则名称")
    @TableField("idx_name")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty("规则代码")
    @TableField("idx_code")
    @JsonProperty("code")
    private String code;

    @ApiModelProperty("规则描述")
    @TableField("idx_desc")
    @JsonProperty("desc")
    private String description;
}
