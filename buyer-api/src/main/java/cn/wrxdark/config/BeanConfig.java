package cn.wrxdark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @ClassName: BeanConfig
 * @Description: TODO
 * @author: yty
 * @Date: 2022/4/15 15:40
 * @Version: 1.0
 */
@Configuration
public class BeanConfig {

    /**
     * 保持库存原子性脚本
     * @return
     */
    @Bean(name = "stockLua")
    public DefaultRedisScript deleteHashAndZSetLua(){
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>();
        longDefaultRedisScript.setResultType(Long.class);
        longDefaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/descstock.lua")));
        return longDefaultRedisScript;
    }

    /**
     * 流量限制脚本
     */
    @Bean
    public DefaultRedisScript<Boolean> limitScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/limit.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

}
