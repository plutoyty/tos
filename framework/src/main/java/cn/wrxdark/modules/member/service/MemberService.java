package cn.wrxdark.modules.member.service;

import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.common.security.token.Token;
import cn.wrxdark.modules.member.entity.dos.Member;
import cn.wrxdark.modules.member.entity.vo.MemberVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * @author 刘宇阳
 * @create 2022/3/23
 */
@Service
public interface MemberService extends IService<Member> {
    Token login(String email, String password);

    Token register(String email, String password);

    Token modifyPassword(String email, String password, String newPassword);

    Member findByUsername(String userName);

    //使用token的
    Member getUserInfo();

    Token loginByEmailCode(String email);

    Token restPassword(String email, String password);

    Member modifyUserInformation(Member member);

    void modifyUserImage(String email, String fileUrl);

    /**
     * @description 获取有分页的用户列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    IPage<Member> getList(Integer pageNum, Integer pageSize);

    /**
     *
     * @param member
     * @param id
     * @return
     */
    MemberVo check(Member member, Integer id);
}
