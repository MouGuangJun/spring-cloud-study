package com.base.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * mybatis-plus代码生成器
 */
//D:\IDEAWorkSpace\payment\payment-base\src\test\java
public class MybatisPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/ezgo?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("gjmou") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://IDEAWorkSpace//ezgo//ezgo-base//src//test//java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    // 连起来就是包的根路径com.mybatis.plus
                    builder.parent("com") // 设置父包名
                            .moduleName("base") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://IDEAWorkSpace//ezgo//ezgo-base//src//test//java//com//base//mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("serial_record") // 设置需要生成的表名
                            .addTablePrefix("ezgo_", "c_") // 设置过滤表前缀
                            .entityBuilder()
                            .disableSerialVersionUID()// 不需要序列化接口
                            //.enableLombok()// 使用lombok
                            .enableTableFieldAnnotation() // 添加属性的数据库字段名
                    ;
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateConfig(builder -> {
                    builder.controller("").service("").serviceImpl("");// 不需要controller和service和impl
                })
                .execute();
    }
}
