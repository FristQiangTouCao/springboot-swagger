package com.hontian.factory;

import com.google.common.collect.Lists;
import com.hontian.config.ApiConfigBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author weed
 * @date 2020/11/16 0016 13:22
 * @description
 */
public class ApiBeanBeanRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * 接口配置信息
     */
    private ApiConfigBean apiConfigBean;

    public void init() {
        apiConfigBean = ApiReader.parseApplication("application.yml");
    }

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        init();
        String enable = apiConfigBean.getEnable();
        if("false".equals(enable)) return;
        Set<Map.Entry<String, String>> entries =
                apiConfigBean.getPackages().entrySet();
        entries.forEach(item -> {
            BeanDefinition beanDefinition = buildBeanDefinition(item.getKey(), item.getValue());
            registry.registerBeanDefinition(item.getValue(),beanDefinition);
        });
    }

    public BeanDefinition buildBeanDefinition(String groupName,String packageName) {
        BeanDefinitionBuilder build = BeanDefinitionBuilder.genericBeanDefinition(Docket.class);
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition)build.getBeanDefinition();
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(groupName);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(packageName);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(apiInfo());
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(securityContext());
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(apiKey());
        beanDefinition.setBeanClass(ApiBeanFactoryBean.class);
        return beanDefinition;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiConfigBean.getTitle())
                .description(apiConfigBean.getDescription())
                .termsOfServiceUrl("http://www.xx.com/")
                .contact(apiConfigBean.getAuth())
                .version(apiConfigBean.getVersion())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }
}
