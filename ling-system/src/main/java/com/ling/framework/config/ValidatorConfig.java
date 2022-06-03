package com.ling.framework.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 校验配置.
 *
 * @author 钟舒艺
 **/
@Configuration
public class ValidatorConfig {


    /**
     * 配置校验框架 快速返回模式.
     *
     * @return 校验
     */
    @Bean
    public Validator validator() {
        final ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
