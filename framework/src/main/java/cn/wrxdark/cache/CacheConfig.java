package cn.wrxdark.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author 刘宇阳
 * @create 2022/3/26
 * @description
 */
@Configuration
public class CacheConfig {

    //private RedisTemplate redisTemplate;
    // 这里面构造器注入 redisTemplate，只是成员变量 属性省略没写
    //为什么省略，因为下面代码中用不着这个成员变量
    @Autowired
    public CacheConfig(RedisTemplate redisTemplate) {   //构造器
        //1.创建jackson序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        //2..创建object mapper
        ObjectMapper objectMapper = new ObjectMapper();
        //3.允许访问对象中所有属性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //4.转换json过程中保存类的信息
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //5.设置value的序列化规则和 key的序列化规则   //key String hashkey:String hashvalue: json序列化
        StringRedisSerializer stringKeySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringKeySerializer);
        //6.jackson2JsonRedisSerializer就是JSON序列号规则，
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //6.5 设置hash类型key value 序列化方式
        redisTemplate.setHashKeySerializer(stringKeySerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        //7工厂创建redisTemplate对象之后在进行配置
        redisTemplate.afterPropertiesSet();
    }
}