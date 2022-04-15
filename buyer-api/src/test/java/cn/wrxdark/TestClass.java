package cn.wrxdark;

import cn.wrxdark.common.entity.enums.ResultCode;
import cn.wrxdark.common.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Test
 * @Description: spring测试类
 * @author: yty
 * @Date: 2022/4/15 17:24
 * @Version: 1.0
 */
@SpringBootTest
public class TestClass {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DefaultRedisScript stockLua;

    @Test
    public void testCurd() {
        String key = "stock";
        List<String> keys = new ArrayList<>();
        keys.add(key);
        Long execute = (Long) redisTemplate.execute(stockLua,keys);
        if(execute == 0){
            throw new ServiceException(ResultCode.GOODS_SKU_QUANTITY_ERROR);
        }
    }
}
