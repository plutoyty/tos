package cn.wrxdark.modules.member.entity.vo;

import cn.wrxdark.modules.member.entity.dos.Member;
import cn.wrxdark.modules.rule.entity.dos.CheckResult;
import lombok.Data;

/**
 * @ClassName: MemberVo
 * @Description:
 * @author: yty
 * @Date: 2022/4/13 21:10
 * @Version: 1.0
 */
@Data
public class MemberVo {
    private Member member;
    private CheckResult checkResult;
}
