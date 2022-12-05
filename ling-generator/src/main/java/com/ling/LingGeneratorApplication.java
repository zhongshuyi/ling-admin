package com.ling;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.BaseEntity;
import com.ling.common.core.mybatisplus.core.BaseMapperPlus;
import java.util.Collections;
import java.util.HashMap;

/**
 * 代码生成启动.
 *
 * @author 钟舒艺
 **/
public class LingGeneratorApplication {

    private static final String URL = "jdbc:mysql://localhost:3306/cake-shop-management?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "12345678";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author("钟舒艺") // 设置作者
                            .disableOpenDir()
                            .outputDir(System.getProperty("user.dir") + "\\ling-generator\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ling") // 设置父包名
                            .moduleName("eims")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "\\ling-generator\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder.addInclude("sys_admin")

                            .entityBuilder()
                            .addSuperEntityColumns("id", "create_by", "create_time", "update_by", "update_time", "is_deleted", "remark")
                            .enableChainModel()
                            .enableLombok()
                            .superClass(BaseEntity.class)
                            .enableFileOverride()
                            .build()

                            .mapperBuilder()
                            .superClass(BaseMapperPlus.class)
                            .build()

                            .controllerBuilder()
                            .superClass(BaseController.class)
                            .build();


                })
                .templateEngine(new VelocityTemplateEngine())

                .templateConfig(builder -> {
                    builder.disable(
                                    TemplateType.ENTITY,
                                    TemplateType.CONTROLLER,
                                    TemplateType.MAPPER,
                                    TemplateType.SERVICE,
                                    TemplateType.SERVICE_IMPL,
                                    TemplateType.XML)
                            .entity("/templates/entity.java")
                            .service("/templates/service.java")
                            .serviceImpl("/templates/serviceImpl.java")
                            .mapper("/templates/mapper.java")
                            .xml("/templates/mapper.xml")
                            .controller("/templates/controller.java")
                            .build();
                })
                .injectionConfig(builder -> {
                    builder.customFile(builderCustomFile ->
                            builderCustomFile
                                    .fileName("DTO.java")
                                    .packageName("dto")
                                    .templatePath("/templates/dto.java.vm")
                                    .enableFileOverride()
                    );

                    builder.customFile(builderCustomFile ->
                            builderCustomFile
                                    .fileName("VO.java")
                                    .packageName("vo")
                                    .templatePath("/templates/vo.java.vm")
                                    .enableFileOverride()
                    );

                    builder.customFile(builderCustomFile ->
                            builderCustomFile
                                    .fileName("Convert.java")
                                    .packageName("convert")
                                    .templatePath("/templates/convert.java.vm")
                                    .enableFileOverride()
                    );

                    HashMap<String, Object> customVar = new HashMap<>(4);
                    customVar.put("usePlus", true);
                    builder.customMap(customVar);
                })
                .execute();
    }
}
