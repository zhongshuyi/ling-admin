package com.mall.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger3 配置类
 *
 * @author 钟舒艺
 * @date 2021-05-08-9:53
 **/
@Configuration
@EnableOpenApi
public class Swagger3Config {

    /**
     * 构建的配置项
     *
     * @return 构建
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.mall.system.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 文档信息
     *
     * @return Api文档的信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("mall")
                .description("钟舒艺的商城学习项目")
                .version("1.0")
                .build();
    }
}
