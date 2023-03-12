package com.ling;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.base.BaseEntity;
import com.ling.common.core.mybatisplus.core.BaseMapperPlus;
import java.util.Collections;
import java.util.HashMap;

/**
 * 代码生成启动.
 *
 * @author 钟舒艺
 **/
public class LingGeneratorApplication {

    /**
     * 数据库连接URL.
     */
    private static final String URL = "jdbc:mysql://localhost/ling-admin?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true";

    /**
     * 用户名.
     */
    private static final String USERNAME = "root";

    /**
     * 密码.
     */
    private static final String PASSWORD = "12345678";

    /**
     * 需要生成的表名，多个表名通过逗号分割.
     */
    private static final String TABLE_NAME = "test_dome";

    /**
     * 模块名称，去除 ‘ling-’ 前缀.
     */
    private static final String MODULE_NAME = "dome";

    public static void main(String[] args) {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author("钟舒艺") // 设置作者
                            .disableOpenDir()
                            .outputDir(System.getProperty("user.dir") + "\\ling-" + MODULE_NAME + "\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ling") // 设置父包名
                            .moduleName(MODULE_NAME)
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "\\ling-" + MODULE_NAME + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder.addInclude(TABLE_NAME)

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

                .templateConfig(builder ->
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
                                .build())
                .injectionConfig(builder -> {
                    builder.customFile(builderCustomFile ->
                            builderCustomFile
                                    .fileName("DTO.java")
                                    .packageName("dto")
                                    .templatePath("/templates/dto.java.vm")
                    );

                    builder.customFile(builderCustomFile ->
                            builderCustomFile
                                    .fileName("VO.java")
                                    .packageName("vo")
                                    .templatePath("/templates/vo.java.vm")
                    );

                    builder.customFile(builderCustomFile ->
                            builderCustomFile
                                    .fileName("Convert.java")
                                    .packageName("convert")
                                    .templatePath("/templates/convert.java.vm")
                    );

                    HashMap<String, Object> customVar = new HashMap<>(4);
                    customVar.put("usePlus", true);
                    builder.customMap(customVar);
                })
                .execute();
    }
}
