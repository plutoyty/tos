package cn.wrxdark.modules.verification.service;

import cn.wrxdark.modules.verification.entity.enums.VerificationEnums;

public interface EmailVerifyService {

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @param uuid  客户端uuid
     * @param type  验证类型
     */
    void sendCode(String email, String uuid, VerificationEnums type);

    /**
     * 验证邮箱验证码，验证成功之后将验证码从缓存清除
     * @param email 邮箱
     * @param uuid  客户端uuid
     * @param code  验证码
     * @param type  验证类型
     * @return 验证是否成功
     */
    boolean verifyCode(String email, String uuid, String code, VerificationEnums type);
}
