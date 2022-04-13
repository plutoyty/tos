package cn.wrxdark.common.propertites;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * token过期配置
 *
 */
@Data
@Configuration
public class JWTTokenProperties {
    /**
     * token默认过期时间
     */
    private long tokenExpireTime = 60;
}
