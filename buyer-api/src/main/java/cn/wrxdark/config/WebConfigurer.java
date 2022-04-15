package cn.wrxdark.config;


import cn.wrxdark.config.interceptor.PayInterceptor;
import com.google.common.util.concurrent.RateLimiter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author 唐奇
 * @description 拦截器配置
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {



    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File file = new File("");
        try {
            registry.addResourceHandler("/image/**").addResourceLocations("file:" + file.getCanonicalPath() + "/data/image/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new PayInterceptor(RateLimiter.create(10000, 1, TimeUnit.SECONDS)))
                .addPathPatterns("/**")
                .excludePathPatterns();
    }
}
