package com.glt.server;

import com.glt.server.service.serviceImpl.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecruitServerApplicationTests {

    @Autowired
    private EmailSenderService emailSenderService;


    @Test
    void contextLoads() {
    }

    @Test
    void SendEmailTest(){
        String code =  emailSenderService.sendEmail("526528786@qq.com");
        System.out.println("发送邮箱获取的验证码"+code);
    }
}
