package cn.wrxdark.config.interceptor;

import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.exception.ServiceException;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘宇阳
 * @description 令牌桶
 */
public class PayInterceptor implements HandlerInterceptor {

    private RateLimiter rateLimiter;

    public PayInterceptor(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean preHandle(HttpServletRequest req,HttpServletResponse resp, Object Handler) throws Exception {

        if (this.rateLimiter.tryAcquire()) {
            return true;
        }
//        throw new ServiceException(ResultCode.ACTIVITY_NOT_START);
        return true;
    }
}
