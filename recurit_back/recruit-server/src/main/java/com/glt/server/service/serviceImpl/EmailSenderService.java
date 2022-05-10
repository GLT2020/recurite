package com.glt.server.service.serviceImpl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private Integer code;
    @Value("${spring.mail.username}")
    private String from="";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private DefaultKaptcha defaultKaptcha;


    public String sendEmail(String to) {
        String text = defaultKaptcha.createText(); // 获取验证码文本内容
//        code = RandomUtil.randomInt(100000, 999999);
//        String text = "1234";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("忘记密码");
        simpleMailMessage.setText("您的验证码为:" + text);
        javaMailSender.send(simpleMailMessage);
        return text;
    }
}
