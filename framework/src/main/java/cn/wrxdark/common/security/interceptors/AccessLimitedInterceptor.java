package cn.wrxdark.common.security.interceptors;

import cn.wrxdark.cache.Cache;
import cn.wrxdark.cache.CachePrefix;
import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.exception.ServiceException;
import cn.wrxdark.common.security.AuthUser;
import cn.wrxdark.common.security.annotations.AccessLimit;
import cn.wrxdark.common.security.context.UserContext;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class AccessLimitedInterceptor implements HandlerInterceptor {

    @Autowired
    private Cache cache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
//            获取注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);

            if (accessLimit == null) return true;
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
//            判断是否需要登录
            if (login) {
                AuthUser currentUser = UserContext.getCurrentUser();
                if (currentUser == null) {
                    throw new ServiceException(ResultCode.USER_NOT_LOGIN);
                }
            }
            String key = getKey(request.getRequestURI());

            Integer count = (Integer) cache.get(key);

//            第一次访问
            if (count == null) {
                cache.put(key, 1, (long) seconds, TimeUnit.SECONDS);
            } else if (count<maxCount){
//                增加访问次数
                cache.update(key, count + 1);
            }else{
//                超出访问限制
                throw new ServiceException(ResultCode.LIMIT_ERROR);
            }

        }
        return true;
    }

    private String getKey(String ip) {
        return CachePrefix.API_ACCESS_LIMIT + "_" + ip;
    }
}
