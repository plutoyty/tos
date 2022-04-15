package cn.wrxdark.modules.rule.entity.dos;

import cn.wrxdark.common.entity.dos.BaseDo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: RuleDTO
 * @Description:
 * @author: yty
 * @Date: 2022/4/13 11:04
 * @Version: 1.0
 */
@Data
@ToString
@TableName("tos_rule")
@ApiModel(value = "接收规则", description="")
public class Rule extends BaseDo implements Serializable {


    @ApiModelProperty("规则名称")
    @TableField("idx_name")
    private String name;


    @ApiModelProperty("规则代码")
    @TableField("idx_code")
    private String code;

    @ApiModelProperty("规则描述")
    @TableField("idx_desc")
    private String description;
}
