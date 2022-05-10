package com.glt.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig  extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/myimg/**").addResourceLocations("file:D:/RecuritProjects/myimg/");
        registry.addResourceHandler("/myimg/**").addResourceLocations("file:D:/IDE/recurit/recruit-server/src/main/resources/static/img/");
        //addResoureHandler：指的是对外暴露的访问路径。addResourceLocations：指的是内部文件放置的目录。设置后，在浏览器窗口输入http://localhost:8008/myimg/文件名，就可以访问到本地地址为E:/IdeaProjects/my-server-save-img/文件名 的文件了。

//        由于这里配置了会覆盖yml给swagger的配置，所有需要再手动添加上配置
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}
