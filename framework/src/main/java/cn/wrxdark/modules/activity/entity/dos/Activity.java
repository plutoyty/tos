package cn.wrxdark.modules.activity.entity.dos;

import cn.wrxdark.common.entity.dos.BaseDo;
import cn.wrxdark.modules.activity.entity.dto.ActivityRuleDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author 刘宇阳
 * @since 2022-03-26
 */
@Data
@ToString(callSuper = true)
@TableName("tos_activity")
@ApiModel(value = "活动对象", description = "活动表")
@NoArgsConstructor
public class Activity extends BaseDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品id")
    @TableField("goods_id")
    @JsonProperty("goods_id")
    private String goodsId;

    @ApiModelProperty("活动名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("封面图片地址")
    @TableField("cover_image")
    @JsonProperty("cover_image")
    private String coverImage;

    @ApiModelProperty("活动描述")
    @TableField("`describe`")
    private String describe;

    @ApiModelProperty("活动产品数量")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty("活动产品数量剩余")
    @TableField("rest_stock")
    @JsonProperty("rest_stock")
    private Integer restStock;

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

    @ApiModelProperty("状态 1开始 0结束")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("乐观锁")
    @TableField("version")
    private Integer version;

    public Activity(ActivityRuleDTO activityRuleDTO) {
        this.goodsId=activityRuleDTO.getGoodsId();
        this.name=activityRuleDTO.getName();
        this.coverImage=activityRuleDTO.getCoverImage();
        this.describe=activityRuleDTO.getDescribe();
        this.stock=activityRuleDTO.getStock();
        this.restStock=activityRuleDTO.getStock();
        this.startTime=activityRuleDTO.getStartTime();
        this.endTime=activityRuleDTO.getEndTime();
    }
}
