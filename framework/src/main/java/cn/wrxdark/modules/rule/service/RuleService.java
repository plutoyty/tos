package cn.wrxdark.modules.rule.service;


import cn.wrxdark.modules.rule.entity.dos.Rule;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

@Service
public interface RuleService {


    void addRule(Rule rule);

    Rule getRuleById(String ruleId);

    void deleteRuleById(String ruleId);

    void updateRule(Rule rule);

    /**
     * huoqu
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Rule> listPage(Integer pageNum, Integer pageSize);
}
