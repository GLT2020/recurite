package com.glt.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class generatorTest {

    //读取配置文件
//    @Autowired
//    private Environment environment;


    @Test
    void insertTest() {
        //设置需要生成的表命list
        List<String> tables = new ArrayList<>();
//        tables.add("admin");
//        tables.add("role");
//        tables.add("menu");
//        tables.add("site");
//        tables.add("worker");
        tables.add("company");
//        tables.add("admin_role");
//        tables.add("company_role");
//        tables.add("company_site");
//        tables.add("companymailbox");
//        tables.add("menu_role");
//        tables.add("recurit_message");
//        tables.add("recurit_message_worker");
//        tables.add("salary_type");
//        tables.add("site_recurit_message");
//        tables.add("worker_work_type");
//        tables.add("workermailbox");
//        tables.add("worker_role");
//        tables.add("work_type");
//        tables.add("sysMessage");
//        tables.add("adminmailbox");

        //获取当前工程路径
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/recurit?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "root")
                .globalConfig(builder -> {
                    builder.author("glt") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .commentDate("yyyy-MM-dd")
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.glt.server") // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .entity("pojo")
                            .service("service")
                            .serviceImpl("serviceImpl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)// 设置需要生成的表名
                            .addTablePrefix("t_", "c_")// 设置过滤表前缀
                            .serviceBuilder()//####################service策略配置
                            .formatServiceFileName("%sService")//serice类名，%s适配，根据表名替换
                            .formatServiceImplFileName("%sServiceImpl")//同上
                            .entityBuilder()//#####################实体类策略配置
                            .enableLombok()//开启lomobok
                            .logicDeleteColumnName("deleted")//说明逻辑删除时哪个字段
                            .enableTableFieldAnnotation()//属性加上说明注解
                            .naming(NamingStrategy.underline_to_camel)//设置映射到实体的命名策略（这里使用驼峰命名）
                            .columnNaming(NamingStrategy.no_change)//字段映射到实体的命名策略（不改变）
                            .controllerBuilder()//#################controller配置策略
                            .formatFileName("%sController")//controller类名，%s适配
//                            .enableHyphenStyle()
                            .enableRestStyle()//开启RestController
                            .mapperBuilder()//#####################mapper策略配置
//                            .superClass(BaseMapper.class)//继承哪个父类
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")//XML名
                            .enableMapperAnnotation()//@mapper开启
                    ;


                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();


    }
}