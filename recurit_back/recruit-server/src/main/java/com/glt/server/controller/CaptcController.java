package com.glt.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class CaptcController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha",produces = "image/jpeg")//produces指定返回值类型和编码
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        // 定义 response 输出类型为 image/jpeg 类型
        response.setDateHeader("Expires", 0);

        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        //设置返回的为jpeg
        response.setContentType("image/jpeg");

        // 生成验证码 开始
        String text = defaultKaptcha.createText(); // 获取验证码文本内容
        System.out.println("验证码文本内容：" + text);
        //将文本验证码放入session
        request.getSession().setAttribute("captcha", text);
        System.out.println();
        //根据文本验证码内容创建图需验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 输出流输出图片，格式为jpg
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 生成验证码 结束
    }
}
