package com;

import cn.wrxdark.modules.goods.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 刘宇阳
 * @create 2022/3/22
 * @description mybatis-plus测试类
 * 用不了 TODO
 */
//@ExtendWith(SpringExtension.class)
//@MybatisPlusTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration("classpath:application.yml")
@SpringBootTest
class MPTest {
    @Autowired
    private GoodsService goodsService;

    @Test
    public void testCurd() {

    }
}
