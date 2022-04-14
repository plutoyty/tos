package cn.wrxdark.modules.rule.mapper;

import cn.wrxdark.modules.rule.entity.dos.Rule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RuleMapper extends BaseMapper<Rule> {

    @Update("update tos_rule set is_deleted=1 where id=#{id}")
    void deleteRuleById(Integer id);

//    @Select("select * from tos_rule where is_deleted=0 and id=#{id}")
//    Rule selectById(@Param("id") Integer id);
}
