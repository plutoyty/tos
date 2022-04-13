package cn.wrxdark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


//启动Spring Boot应用程序只需要一行代码加上一个注解@SpringBootApplication，
//该注解实际上又包含了：
//@SpringBootConfiguration
//@Configuration
//@EnableAutoConfiguration
//@AutoConfigurationPackage
//@ComponentScan
//这样一个注解就相当于启动了自动配置和自动扫描。

@SpringBootApplication
@EnableOpenApi
public class BuyerApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuyerApiApplication.class, args);
    }
}