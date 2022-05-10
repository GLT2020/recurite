package com.glt.server.config.security;


import com.glt.server.config.security.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDenieHandler restfulAccessDenieHandler;

//    @Autowired
//    private CustomerFilter customerFilter;

    @Autowired
    private CustomUrlDescisionManager customUrlDescisionManager;



    //设置放行
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/worker/register",
                "/company/register",
                "/company/uploadImg",
                "/company/sendEmail",
                "/company/notPassword",
                "/worker/notPassword",
                "/worktype/",
//                "**.png",
                "/static/img/**",
                "/myimg/**",
                "/css/**",
                "/js/**",
                "/index.html",
                "/favicon.ico",
                "/swagger-ui.html/**",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/**",
                "/captcha",
                "/ws/**"
        );
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用jwt，不需要csrf
        http.csrf()
                .disable()
                //基于token,不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                //所有请求都要求认证
                .anyRequest()
                .authenticated()

                //动态权限配置
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
//                        object.setAccessDecisionManager(customUrlDescisionManager);
////                        object.setSecurityMetadataSource(customerFilter);
//                        return object;
//                    }
//                })
                .and()
                //禁用缓存
                .headers()
                .cacheControl();

        //添加jwt 登录授权过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //添加自定义未授权和登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDenieHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //重写这个方法并暴露
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
