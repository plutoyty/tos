package cn.wrxdark.mybatis.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"cn.wrxdark.modules.*.mapper","cn.wrxdark.modules.*.*.mapper"})
public class MybatisPlusConfig {
    /**
     * 关于MP分页规则说明
     *   规则: 需要设定一个拦截器.将分页的Sql进行动态的拼接.
     *  Sql: 规则现在的Sql都支持Sql92标准!!!! 设计理念不同
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
