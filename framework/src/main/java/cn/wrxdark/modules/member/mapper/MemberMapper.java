package cn.wrxdark.modules.member.mapper;

import cn.wrxdark.modules.member.entity.dos.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author 刘宇阳
 * @since 2022-03-23
 */
@Mapper
@Repository
public interface MemberMapper extends BaseMapper<Member> {
    /**
     * @description 扣减用户余额
     * @author 刘宇阳
     * @param memberId
     * @param price
     */
    @Update("UPDATE tos_member SET balance=balance-#{price} WHERE id=#{memberId}")
    void decrBalance(@Param("memberId") String memberId,@Param("price") double price);

    /**
     * @description 通过邮箱获得对应的用户
     * @param email
     * @return
     */
    @Select("select * from tos_member where email=#{email}")
    Member selectByEmail(@Param("email") String email);

    /**
     * @description 修改密码
     * @param email
     * @param password
     * @return
     */
    @Update("update tos_member set password=#{pwd} where email=#{email}")
    boolean updatePassword(@Param("email") String email, @Param("pwd") String password);

    /**
     * @description 修改头像
     * @param email
     * @param avatar
     * @return
     */
    @Update("update tos_member set avatar=#{avatar} where email=#{email}")
    boolean updateImage(@Param("email") String email, @Param("avatar") String avatar);
}