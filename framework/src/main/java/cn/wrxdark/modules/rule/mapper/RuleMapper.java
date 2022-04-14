package cn.wrxdark.modules.rule.mapper;

import cn.wrxdark.modules.rule.mapper.entity.dos.Rule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author 刘宇阳
 * @create 2022/4/14
 * @description
 */
@Mapper
@Component
public interface RuleMapper extends BaseMapper<Rule> {
    @Insert("INSERT INTO tos_activity_rule(activity_id,rule_id) VALUES(#{activityId},#{ruleId})")
    void insertActivityRuleRelation(String activityId,String ruleId);
}
