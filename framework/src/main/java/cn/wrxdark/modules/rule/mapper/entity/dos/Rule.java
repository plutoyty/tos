package cn.wrxdark.modules.rule.mapper.entity.dos;

import cn.wrxdark.common.entity.dos.BaseDo;
import cn.wrxdark.modules.activity.entity.dto.ActivityRuleDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 刘宇阳
 * @create 2022/4/14
 * @description
 */
@Data
@ToString
@TableName("tos_rule")
@ApiModel(value = "活动规则对象", description = "")
@NoArgsConstructor
public class Rule extends BaseDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("规则名称")
    @TableField("idx_name")
    private String name;

    @ApiModelProperty("规则代码")
    @TableField("idx_code")
    private String code;

    @ApiModelProperty("规则描述")
    @TableField("idx_desc")
    private String description;

    public Rule(ActivityRuleDTO activityRuleDTO) {
        name=activityRuleDTO.getName();
        code=activityRuleDTO.getCode();
        description=activityRuleDTO.getDescription();
    }
}