package com.glt.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                //允许任何域名使用
                .allowedOriginPatterns("*")
                //允许post、get等方法
                .allowedMethods("POST","PUT","GET","DELETE","OPTIONS")
                //允许任何头
                .allowedHeaders("*");
    }
}
