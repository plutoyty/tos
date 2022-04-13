package cn.wrxdark.common.security.token;

import cn.wrxdark.common.security.AuthUser;
import cn.wrxdark.common.security.enums.UserEnums;
import cn.wrxdark.common.security.token.base.AbstractTokenGenerate;
import cn.wrxdark.modules.member.entity.dos.Member;
import org.springframework.stereotype.Component;

/**
 * 会员token生成
 *
 */
@Component
public class MemberTokenGenerate extends AbstractTokenGenerate<Member> {

    private final TokenUtil tokenUtil;

    public MemberTokenGenerate(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }


    @Override
    public Token createToken(Member user, Boolean longTerm) {
        //记录最后登录时间，客户端类型
//        member.setLastLoginDate(new Date());
//        memberService.updateById(member);

        AuthUser authUser = new AuthUser(user.getId(), user.getId(), user.getEmail(), user.getAvatar(), UserEnums.MEMBER, longTerm);
        //登陆成功生成token
        return tokenUtil.createToken(user.getId(), authUser, longTerm, UserEnums.MEMBER);
    }

    @Override
    public Token refreshToken(String refreshToken) {
        return tokenUtil.refreshToken(refreshToken, UserEnums.MEMBER);
    }

}
