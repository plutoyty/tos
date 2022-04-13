package cn.wrxdark.common.security;

import cn.wrxdark.common.security.enums.UserEnums;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 582441893336003319L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * id
     */
    private String id;

    /**
     * @see UserEnums
     * 角色
     */
    private UserEnums role;

    private String storeId;

    private String storeName;

    /**
     * 是否是长登录
     */
    private Boolean longTerm;

    public AuthUser(String username, String id, String email, String avatar, UserEnums role, Boolean longTerm) {
        this.username = username;
        this.avatar = avatar;
        this.id = id;
        this.role = role;
        this.email = email;
        this.longTerm = longTerm;
    }
}