package cn.wrxdark.modules.rule.service.Impl;

import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.modules.activity.entity.dos.Activity;
import cn.wrxdark.modules.rule.entity.dos.Rule;
import cn.wrxdark.modules.rule.mapper.RuleMapper;
import cn.wrxdark.modules.rule.service.RuleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: RuleServiceImpl
 * @Description:
 * @author: yty
 * @Date: 2022/4/13 12:09
 * @Version: 1.0
 */
@Service
@Slf4j
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleMapper ruleMapper;

    /**
     * 添加规则
     * @param rule
     */
    @Override
    public void addRule(Rule rule) {
        if (rule.getName().equals("") || rule.getDescription().equals("") || rule.getCode().equals("")) {
            throw new ServiceException(ResultCode.RULE_NOT_COMPLETE);
        }
        ruleMapper.insert(rule);
    }

    /**
     * 根据id获取规则
     * @param ruleId
     * @return
     */
    @Override
    public Rule getRuleById(String ruleId) {
        if (ruleId == null || ruleId == "") {
            throw new ServiceException(ResultCode.RULE_ID_NULL);
        }
        return ruleMapper.selectById(Integer.valueOf(ruleId.trim()));
    }

    /**
     * 删除规则
     * @param ruleId
     */
    @Override
    public void deleteRuleById(String ruleId) {
        if (ruleId == null || ruleId == "") {
            throw new ServiceException(ResultCode.RULE_ID_NULL);
        }
        ruleMapper.deleteRuleById(Integer.valueOf(ruleId.trim()));
    }

    @Override
    public void updateRule(Rule rule) {
        if (rule.getName().equals("") || rule.getDescription().equals("") || rule.getCode().equals("")) {
            throw new ServiceException(ResultCode.RULE_NOT_COMPLETE);
        }
        ruleMapper.updateById(rule);
    }

    /**
     * 规则的分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Rule> listPage(Integer pageNum, Integer pageSize) {
        IPage<Rule> iPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        iPage = ruleMapper.selectPage(iPage, queryWrapper);
        return iPage;
    }


}
