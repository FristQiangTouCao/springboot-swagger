package com.hontian.annotation;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.hontian.factory.ApiBeanBeanRegistrar;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author weed
 * @date 2020/11/16 0016 17:49
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableKnife4j
@EnableSwagger2
@Import({BeanValidatorPluginsConfiguration.class, ApiBeanBeanRegistrar.class})
public @interface EnableHongTianApi {
}
