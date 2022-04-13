package cn.wrxdark.modules.member.service.impl;

import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.entity.enums.ResultUtil;
import cn.wrxdark.common.entity.vo.ResultMessage;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.common.security.AuthUser;
import cn.wrxdark.common.security.context.UserContext;
import cn.wrxdark.common.security.token.MemberTokenGenerate;
import cn.wrxdark.common.security.token.Token;
import cn.wrxdark.modules.goods.entity.dos.Goods;
import cn.wrxdark.modules.member.entity.dos.Member;
import cn.wrxdark.modules.member.mapper.MemberMapper;
import cn.wrxdark.modules.member.service.MemberService;

import cn.wrxdark.util.SM2Util;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Repository
@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    // 连接mapper
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberTokenGenerate memberTokenGenerate;

    /**
     * 登录
     */
    @Override
    public Token login(String email, String password) {
        Member member = memberMapper.selectByEmail(email);  //查询信息
        //判断用户是否存在
        if (member == null) {
            throw new ServiceException(ResultCode.USER_EMAIL_NOT_EXIST);
        }
        if (!SM2Util.verify(password+member.getSalt(),member.getPassword())) {
            throw new ServiceException(ResultCode.USER_PASSWORD_ERROR);
        }
        //创建一个token
        return memberTokenGenerate.createToken(member, false);
    }


    @Override
    public Token register(String email, String password) {
        Member member = memberMapper.selectByEmail(email);
        //检查是否有这个账号
        if (member != null) { //账号存在
            throw new ServiceException(ResultCode.USER_EMAIL_EXIST);
        }
        member = new Member();
        member.setEmail(email);
        member.setSalt(getSalt());
        member.setPassword(SM2Util.encrypt(password+member.getSalt()));
        member.setName(UUID.randomUUID().toString());
        memberMapper.insert(member);
        return memberTokenGenerate.createToken(member, false);
    }

    @Override
    public Token modifyPassword(String email, String password, String newPassword) {
        Member member = memberMapper.selectByEmail(email);  //查询信息
        if (member == null) {
            throw new ServiceException(ResultCode.USER_EMAIL_NOT_EXIST);
        }
        //判断旧密码是否正确
        if (!SM2Util.verify(password+member.getSalt(),member.getPassword())) {
            throw new ServiceException(ResultCode.USER_OLD_PASSWORD_ERROR);
        }
        //修改密码
        newPassword=SM2Util.encrypt(newPassword+member.getSalt());
        member.setPassword(newPassword);
        memberMapper.updatePassword(email, newPassword);
        return this.login(member.getEmail(),member.getPassword());
    }

    @Override
    public Member findByUsername(String userName) {
        return null;
    }


    //使用token的
    @Override
    public Member getUserInfo() {
        AuthUser tokenUser = UserContext.getCurrentUser();
        if (tokenUser == null) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
        return this.getById(tokenUser.getId());
    }

    @Override
    public Token loginByEmailCode(String email) {
        Member member = memberMapper.selectByEmail(email);
        if(member==null){
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        return memberTokenGenerate.createToken(member,false);
    }

    @Override
    public Token restPassword(String email, String password) {
        Member member = memberMapper.selectByEmail(email);
        if (member == null) {
            throw new ServiceException(ResultCode.USER_EMAIL_NOT_EXIST);
        }
        password=SM2Util.encrypt(password+member.getSalt());
        member.setPassword(password);
        memberMapper.updatePassword(email,password);
        return memberTokenGenerate.createToken(member, false);
    }


    @Override
    public Member modifyUserInformation(Member member) {
        // 条件构造器
        memberMapper.updateById(member);
        return member;
    }

    @Override
    public void modifyUserImage(String email, String fileUrl){
        memberMapper.updateImage(email,fileUrl);
    }

    /**
     * @description 获取有分页的用户列表
     * @author 刘宇阳
     * @param pageNum 当前页数
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public IPage<Member> getList(Integer pageNum, Integer pageSize) {
        IPage<Member> iPage = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        //经过MP分页查询将所有的分页(total/结果/页面/条数/xxx)数据封装到iPage对象
        iPage = memberMapper.selectPage(iPage,queryWrapper);
        log.info("获得了用户列表");
        return iPage;
    }

    private String model = "abcdefghijklmnopqrstuvwxyz1234567890";
    private int saltSize = 4;
    public String getSalt() {
        StringBuffer salt = new StringBuffer();
        char[] m = model.toCharArray();
        for (int i = 0; i < saltSize; i++) {
            char c = m[(int) (Math.random() * 36)];
            salt = salt.append(c);
        }
        return salt.toString();
    }

    //token
    public Token modifyPassword_token(String password, String newPassword) {
        AuthUser tokenUser = UserContext.getCurrentUser();
        if (tokenUser == null) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
        // Member member = userMapper.getById(tokenUser.getId());
       /* //判断旧密码输入是否正确
        if (!new BCryptPasswordEncoder().matches(oldPassword, member.getPassword())) {
            throw new ServiceException(ResultCode.USER_OLD_PASSWORD_ERROR);
        }
        //修改会员密码
        LambdaUpdateWrapper<Member> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(Member::getId, member.getId());
        lambdaUpdateWrapper.set(Member::getPassword, new BCryptPasswordEncoder().encode(newPassword));
        this.update(lambdaUpdateWrapper);
        return member;*/
        return null;
    }
}