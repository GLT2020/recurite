package com.glt.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.glt.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(secruityContexts())
                .securitySchemes(securitySchemes());
    }



    //配置swagger信息 = apiInfo
//    private ApiInfo apiInfo(){
//
//        //作者信息
//        Contact contact = new Contact("glt", "http://glt.blog.com", "526528786@qq.com");
//
//        return new ApiInfo(
//                "云E办API文档",
//                "API文档",
//                "1.0",
//                "urn:tos",
//                contact,
//                "Apache 2.0",
//                "http://localhost:8081/",
//                new ArrayList()
//        );
//    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("招聘系统接口文档")
                .description("招聘系统接口文档")
                .contact(new Contact("glt","http:locahost:8081/doc.html","XXX@XX.com"))
                .version("1.0")
                .build();
    }

    // 1. 解决访问接口登录问题
    private List<ApiKey> securitySchemes(){
        //设置请求头信息
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
        result.add(apiKey);
        return result;
    }

    // 2. 解决访问接口登录问题
    private List<SecurityContext> secruityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/hello/.*"));
        return result;
    }

    // 4. 设置默认授权 - 解决访问接口登录问题
    private SecurityContext getContextByPath(String s) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(s))
                .forPaths(PathSelectors.regex("/login/.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("gobal", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;

    }

}
