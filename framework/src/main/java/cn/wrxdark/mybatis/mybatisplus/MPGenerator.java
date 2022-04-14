package cn.wrxdark.mybatis.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 刘宇阳
 * @description 代码生成器
 */
public class MPGenerator {
    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();
        //设置表名
        tables.add("tos_rule");
        //配置模板
        FastAutoGenerator.create("jdbc:mysql://101.33.228.113:3306/miaosha?characterEncoding=utf8","root","tqyyds")
                .globalConfig(builder -> {
                    builder.author("刘宇阳")//作者
                            //输出目录，这里的tmp和最外面的README.md是同级的，放心测试
                            .outputDir(System.getProperty("user.dir")+"\\tmp\\main\\java\\")
                            .enableSwagger()           //开启swagger
                            .commentDate("yyyy-MM-dd");
//                            .fileOverride();            //开启覆盖之前生成的文件
                })
                .packageConfig(builder -> {
                    builder.parent("cn") //父包名
                            .moduleName("wrxdark")//父模块名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("serviceImpl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir")+"\\tmp\\main\\resources\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .addTablePrefix("")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .enableLombok()
//                            .logicDeleteColumnName("is_deleted")
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .mapperBuilder()
                            .enableBaseResultMap()  //生成通用的resultMap
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}