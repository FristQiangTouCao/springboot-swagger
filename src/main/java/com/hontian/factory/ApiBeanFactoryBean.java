package com.hontian.factory;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.FactoryBean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author weed
 * @date 2020/11/16 0016 13:31
 * @description
 */
public class ApiBeanFactoryBean implements FactoryBean<Docket> {


    private String groupName;

    private String packageName;

    private ApiInfo apiInfo;

    private SecurityContext securityContext;

    private ApiKey apiKey;

    private ApiBeanFactoryBean(String groupName,String packageName,ApiInfo apiInfo,SecurityContext securityContext,ApiKey apiKey) {
        this.groupName = groupName;
        this.packageName = packageName;
        this.apiInfo =apiInfo;
        this.securityContext =securityContext;
        this.apiKey = apiKey;
    }

    public Docket getObject() throws Exception {
       return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Lists.newArrayList(securityContext)).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey));
    }

    public Class<?> getObjectType() {
        return Docket.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
