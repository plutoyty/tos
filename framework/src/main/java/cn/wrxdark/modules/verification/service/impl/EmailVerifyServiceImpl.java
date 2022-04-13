package cn.wrxdark.modules.verification.service.impl;

import cn.wrxdark.cache.Cache;
import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.modules.verification.entity.enums.VerificationEnums;
import cn.wrxdark.modules.verification.service.EmailVerifyService;
import cn.wrxdark.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailVerifyServiceImpl implements EmailVerifyService {

    @Autowired
    private Cache cache;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendCode(String email, String uuid, VerificationEnums type) {
        String key = RedisKeyUtil.generateEmailKey(email, uuid, type);
        String code = getCode();
        sendEmail("3113759501@qq.com", email, "Tos验证码", type.name() + ":" + code);
        cache.put(key, code, 10L, TimeUnit.MINUTES);
    }

    @Override
    public boolean verifyCode(String email, String uuid, String code, VerificationEnums type) {
        String key = RedisKeyUtil.generateEmailKey(email, uuid, type);
        Object val = cache.get(key);
        if (val == null) {
            throw new ServiceException(ResultCode.VERIFICATION_NOT_EXIST);
        }
        if (val.equals(code)) {
            cache.remove(key);
            return true;
        }
        return false;
    }

    private String getCode() {
        return String.valueOf(Math.abs((new Random()).nextInt() % (int) 1e6));
    }

    private void sendEmail(String from, String to, String subject, String context) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setText(context);
        message.setSubject(subject);
        javaMailSender.send(message);
    }
}
