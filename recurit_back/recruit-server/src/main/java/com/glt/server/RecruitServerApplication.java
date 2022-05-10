package com.glt.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan("com.glt.server.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RecruitServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitServerApplication.class, args);
    }

}
