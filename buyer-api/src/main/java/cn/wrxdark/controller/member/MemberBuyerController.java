package cn.wrxdark.controller.member;


import cn.wrxdark.cache.Cache;
import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.common.security.token.Token;
import cn.wrxdark.modules.member.entity.dos.Member;
import cn.wrxdark.modules.member.service.MemberService;
import cn.wrxdark.modules.verification.entity.enums.VerificationEnums;
import cn.wrxdark.modules.verification.service.EmailVerifyService;
import cn.wrxdark.util.SM3Util;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 买家段，用户接口
 */
@RestController
@RequestMapping("/buyer/member")
@Api(tags = "用户接口")
@Slf4j
public class MemberBuyerController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EmailVerifyService emailVerifyService;

    /**
     * @description  获得用户带分页的列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    @Operation(summary = "获得用户带分页的列表")
    @GetMapping("/list")
    public ResultMessage<IPage<Member>> getList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ){
        IPage<Member> memberIPage=memberService.getList(pageNum,pageSize);
        return ResultUtil.data(memberIPage);
    }


    /**
     * 获取当前登录用户接口
     * @return 当前登录用户
     */
    @GetMapping
    @Operation(summary = "获取当前登录用户")
    public ResultMessage<Member> getUserInfo() {
        return ResultUtil.data(memberService.getUserInfo());
    }

    /**
     * 用户注册
     *
     * @param password 密码
     * @param email    邮箱
     * @param code     验证码
     * @return 登录token
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ResultMessage<Token> register(@NotNull(message = "密码不能为空")  @RequestParam @Parameter(description = "密码") String password,
                                         @NotNull(message = "邮箱不能为空")  @RequestParam @Parameter(description = "邮箱") String email,
                                         @NotNull(message = "验证码不能为空") @RequestParam @Parameter(description = "验证码") String code,
                                         @RequestHeader String uuid) {
//        验证用户注册验证码
        if (!emailVerifyService.verifyCode(email, uuid, code, VerificationEnums.REGISTER)) {
            throw new ServiceException(ResultCode.VERIFICATION_EMAIL_CHECKED_ERROR);
        }
        return ResultUtil.data(memberService.register(email, password));
    }


    /**
     * 用户名登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return 登录token
     */
    @PostMapping("/user-login")
    @Operation(summary = "用户名登录")
    public ResultMessage<Token> userLogin(@NotNull(message = "邮箱不能为空") @RequestParam @Parameter(description = "邮箱") String email,
                                          @NotNull(message = "密码不能为空") @RequestParam @Parameter(description = "密码") String password
    ) {
        return ResultUtil.data(memberService.login(email, password));
    }

    /**
     * 邮箱验证码登录
     *
     * @param email 邮箱
     * @param code  验证码
     */
    @PostMapping("/email-login")
    @Operation(summary = "邮箱验证码登录")
    public ResultMessage<Token> emailLogin(@NotNull(message = "邮箱为空") @RequestParam @Parameter(description = "邮箱") String email,
                                           @NotNull(message = "验证码为空") @RequestParam @Parameter(description = "验证码") String code,
                                           @RequestHeader String uuid) {
        if (!emailVerifyService.verifyCode(email, uuid, code, VerificationEnums.LOGIN)) {
            throw new ServiceException(ResultCode.VERIFICATION_EMAIL_CHECKED_ERROR);
        }
        return ResultUtil.data(memberService.loginByEmailCode(email));
    }


    /**
     * 修改密码   no token
     *
     * @param password    旧密码
     * @param newPassword 新密码
     * @return 新token
     */
    @PutMapping("/modify-password")
    @Operation(summary = "修改密码")
    public ResultMessage<Token> modifyPass(@NotNull(message = "旧密码不能为空") @RequestParam @Parameter(description = "旧密码") String password,
                                           @NotNull(message = "新密码不能为空") @RequestParam @Parameter(description = "新密码") String newPassword,
                                           @NotNull(message = "邮箱不能为空") @RequestParam @Parameter(description = "邮箱") String email) {

        return ResultUtil.data(memberService.modifyPassword(email, password, newPassword));
    }

    /**
     * 通过邮箱重置密码
     *
     * @param email 邮箱
     * @param code  验证码
     * @param uuid  设备id
     * @return 新token
     */
    @PostMapping("/reset-password")
    @Operation(summary = "通过邮箱重置密码")
    public ResultMessage<Token> resetByEmail(@NotNull(message = "邮箱为空") @RequestParam @Parameter(description = "邮箱") String email,
                                             @NotNull(message = "验证码为空") @RequestParam @Parameter(description = "验证码") String code,
                                             @NotNull(message = "密码为空") @RequestParam @Parameter(description = "密码") String password,
                                             @RequestHeader String uuid) {
        if (!emailVerifyService.verifyCode(email, uuid, code, VerificationEnums.UPDATE_PASSWORD)) {
            throw new ServiceException(ResultCode.VERIFICATION_EMAIL_CHECKED_ERROR);
        }
        return ResultUtil.data(memberService.restPassword(email, password));
    }

    /**
     * 通过账号修改个人资料
     * @param member
     * @return
     */
    @PostMapping("/modify-UserInformation")
    @Operation(summary = "通过账号修改个人资料")
    public ResultMessage modifyUserInfor(@RequestBody Member member){
//        public ResultMessage<Member> modifyUserInfor(@RequestBody Member member){
        memberService.modifyUserInformation(member);
        log.info("修改成功");
        return ResultUtil.success();
//        return  ResultUtil.data(memberService.modifyUserInformation(member));
    }

    /**
     * 修改用户的头像
     * @param email
     * @param fileUrl
     * @return
     */
    @PostMapping("/modify-Image")
    @Operation(summary = "修改用户的头像")
    public ResultMessage modifyUserImage(String email,String fileUrl){

        memberService.modifyUserImage(email,fileUrl);
        log.info("修改成功");
        return ResultUtil.success();
    }
}
