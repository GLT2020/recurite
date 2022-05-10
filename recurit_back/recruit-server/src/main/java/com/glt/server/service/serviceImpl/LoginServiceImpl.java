package com.glt.server.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.glt.server.config.security.component.JwtTokenUtil;
import com.glt.server.pojo.*;
import com.glt.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public RespBean adminLogin(LoginParam loginParam, NameHelper nameHelper, HttpServletRequest request) {
        //验证码验证
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println("captcha->>>>>>>>>>>>>>>>"+captcha);
        System.out.println("登录验证的captcha"+captcha+">>>session中的:"+request.getSession().getAttribute("captcha"));
        if (StringUtils.isEmpty(loginParam.getCode()) || !captcha.equalsIgnoreCase(loginParam.getCode())){
            return RespBean.error("验证码输入错误，请重新输入！");
        }

        //authenticate进行用户认证
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(JSON.toJSONString(nameHelper), loginParam.getPassword());
        //这里的authenticate会调用 loadUserByUsername 的方法进行验证
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        LoginUser userDetails = (LoginUser) userDetailsService.loadUserByUsername(JSON.toJSONString(nameHelper));
        //如果验证没通过，给出提示
//        if(Objects.isNull(authenticate)){
//            return RespBean.error("登录失败，请重试");
//        }
//        System.out.println("登录验证的userdetails->>>"+userDetails);
//        System.out.println("登录验证的userdetails密码与输入密码验证->>>"+passwordEncoder.matches(loginParam.getPassword(),userDetails.getPassword()));
        //登录验证
        if(null == userDetails || !passwordEncoder.matches(loginParam.getPassword(),userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账户被禁用，请联系管理员！");
        }

        //如果验证通过，使用userid生成jwt，jwt存入respBean返回
//        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
//        System.out.println(">>>>>>>>>>>>>>>>>>登录中的loginUser:"+loginUser);
//        String id = loginUser.getId().toString();

        String jwt = jwtTokenUtil.generateToken(userDetails,nameHelper,userDetails.getId());
        Map<String, String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("tokenHead",tokenHead);
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        System.out.println("登录的userDetails的权限->>>>"+userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        if(nameHelper.getState() == 0){ //设置系统用户存入redis
            //把完整的用户信息存入redis，userid作为key
            redisTemplate.opsForValue().set("login0:"+userDetails.getId(),userDetails);

        }
        else if(nameHelper.getState() == 1){ //企业用户
            redisTemplate.opsForValue().set("login1:"+userDetails.getId(),userDetails);

        }
        else{ //工人用户
            redisTemplate.opsForValue().set("login2:"+userDetails.getId(),userDetails);

        }
        return RespBean.success("登录成功",map);
    }
}
