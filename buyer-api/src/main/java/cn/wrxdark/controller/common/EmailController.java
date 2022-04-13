package cn.wrxdark.controller.common;

import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.modules.verification.entity.enums.VerificationEnums;
import cn.wrxdark.modules.verification.service.EmailVerifyService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 邮箱验证码接口
 */
@RestController
@RequestMapping("/common/email")
@Api(tags = "邮箱验证码接口")
public class EmailController {

    @Autowired
    private EmailVerifyService emailVerifyService;
    /**
     * @param email             目标邮箱
     * @param verificationEnums 验证码类型
     * @return 操作结果
     */
    @GetMapping("/{verificationEnums}")
    @Operation(summary = "获取邮箱验证码")
    public ResultMessage getEmailCode(
            @PathVariable VerificationEnums verificationEnums,
            @RequestParam String email,
            @RequestHeader String uuid){
        emailVerifyService.sendCode(email,uuid,verificationEnums);
        return ResultUtil.success();
    }
}
