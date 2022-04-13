package cn.wrxdark.common.security.context;


import cn.wrxdark.common.security.AuthUser;
import cn.wrxdark.common.security.enums.SecurityEnum;
import cn.wrxdark.common.security.token.SecretKeyUtil;
import com.google.gson.Gson;
import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户上下文
 */
public class UserContext {

    /**
     * 根据request获取用户信息
     * @return 授权用户
     */
    public static AuthUser getCurrentUser() {
        String accessToken = getCurrentUserToken();
        return getAuthUser(accessToken);
    }

    public static String getCurrentUserToken() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request.getHeader(SecurityEnum.HEADER_TOKEN.getValue());
        }
        return null;
    }

    /**
     * 根据jwt获取token的用户信息
     *
     * @param accessToken token
     * @return 授权用户
     */
    public static AuthUser getAuthUser(String accessToken) {
        try {
            //解密出token里的claims
            Claims claims
                    = Jwts.parser()
                    .setSigningKey(SecretKeyUtil.generalKeyByDecoders())
                    .parseClaimsJws(accessToken).getBody();
            //获取存储在claims中的用户信息
            String json = claims.get(SecurityEnum.USER_CONTEXT.getValue()).toString();
            return new Gson().fromJson(json, AuthUser.class);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
    }
}
