package cn.wrxdark.modules.member.entity.dos;

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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author 刘宇阳
 * @since 2022-03-23
 */
@Data
@ToString(callSuper = true)
@TableName("tos_member")
@ApiModel(value = "TosMember对象", description = "用户")
public class Member extends BaseDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("个人邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("密码")
    @TableField("`password`")
    private String password;

    @ApiModelProperty("姓名")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("身份证号")
    @TableField("id_card")
    @JsonProperty("id_card")
    private String idCard;

    @ApiModelProperty("性别")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("年龄")
    @TableField("age")
    private Integer age;

    @ApiModelProperty("头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("电话")
    @TableField("phone")
    private Long phone;

    @ApiModelProperty("随机盐")
    @TableField("salt")
    private String salt;

    @ApiModelProperty("余额")
    @TableField("balance")
    private Double balance;

    @ApiModelProperty("工作状态(0 失业，1 有工作)")
    @TableField("work_condition")
    @JsonProperty("work_condition")
    private Integer workCondition;

    @ApiModelProperty("是否失信(0 否，1 是)")
    @TableField("dishonest")
    private Integer dishonest;
}
