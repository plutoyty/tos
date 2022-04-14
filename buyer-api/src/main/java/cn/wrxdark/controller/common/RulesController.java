package cn.wrxdark.controller.common;

import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.rule.entity.dos.Rule;
import cn.wrxdark.modules.rule.service.RuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: RulesController
 * @Description: 规则接口
 * @author: yty
 * @Date: 2022/4/13 10:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/common/rule")
@Slf4j
@Api(tags = "规则接口")
public class RulesController {

    @Autowired
    private RuleService ruleService;

//    @PostMapping("/path/{memberId}/{activityId}/{goodsId}")
//    @AccessLimit(seconds = 5, maxCount = 5, needLogin = false)
//    public ResultMessage<String> getSeckillTempPath(
//            @NotNull(message = "请求参数不足") @PathVariable("memberId") String memberId,
//            @NotNull(message = "请求参数不足") @PathVariable("activityId") String activityId,
//            @NotNull(message = "请求参数不足") @PathVariable("goodsId") String goodsId
//    ) {
//        String path=seckillService.getPath(memberId,goodsId,activityId);
//        return ResultUtil.data(path);
//    }

    /**
     * 添加规则
     *
     * @param rule
     * @return
     */
    @Operation(summary = "添加规则")
    @PostMapping("/add")
    public ResultMessage addRule(@RequestBody Rule rule) {
        ruleService.addRule(rule);
        return ResultUtil.success();
    }

    /**
     * 根据id获取规则
     *
     * @param ruleId
     * @return
     */
    @Operation(summary = "根据id获取规则")
    @GetMapping("/find/{ruleId}")
    public ResultMessage getRule(@PathVariable("ruleId") String ruleId) {
        return ResultUtil.data(ruleService.getRuleById(ruleId));
    }

    /**
     * 删除该id的规则
     *
     * @param ruleId
     * @return
     */
    @Operation(summary = "删除规则")
    @PostMapping("/delete/{ruleId}")
    public ResultMessage deleteRule(@PathVariable("ruleId") String ruleId) {
        ruleService.deleteRuleById(ruleId);
        log.info("成功");
        return ResultUtil.success();
    }

    /**
     * @param rule
     * @return
     */
    @Operation(summary = "获得规则的分页列表")
    @PutMapping("/update")
    public ResultMessage updateRule(@RequestBody Rule rule) {
        ruleService.updateRule(rule);
        return ResultUtil.success();
    }

    /**
     * 规则的分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Operation(summary = "获得规则的分页列表")
    @GetMapping("/list")
    public ResultMessage<IPage<Rule>> getList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        IPage<Rule> ipage = ruleService.listPage(pageNum, pageSize);
        log.info("获取规则列表成功");
        return ResultUtil.data(ipage);
    }

}
