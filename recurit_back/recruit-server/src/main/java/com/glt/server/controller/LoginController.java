package com.glt.server.controller;

import com.glt.server.config.security.component.JwtTokenUtil;
import com.glt.server.pojo.*;

import com.glt.server.service.LoginService;
import com.glt.server.service.serviceImpl.EmailSenderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @ApiOperation(value = "登录后返回token")
    @PostMapping("/login")
    public RespBean AdminLogin(@RequestBody LoginParam loginParam, HttpServletRequest request){
        System.out.println(loginParam);
        NameHelper nameHelper = new NameHelper();
        nameHelper.setPhone(loginParam.getPhone());
        nameHelper.setState(loginParam.getState());
        RespBean respBean = loginService.adminLogin(loginParam,nameHelper,request);
        return  respBean;
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public LoginUser getAdminInfo(Principal principal,HttpServletRequest request){
        if(null == principal) return null;

        String authHeader = request.getHeader(tokenHeader);
        String authToken = authHeader.substring(tokenHead.length());
        Integer state = jwtTokenUtil.getStateFromToken(authToken);
        int userid = jwtTokenUtil.getIdFromToken(authToken);

        LoginUser loginUser = ((LoginUser) redisTemplate.opsForValue().get("login"+state+":" + userid));
//        Admin admin = adminService.getAdminByUserName(username);
//        admin.setPassword(null);
////        获取用户的角色
//        admin.setRoles(adminService.getRoles(admin.getId()));
        return loginUser;
    }


    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public RespBean logout(){
//        //获取securityContext中的用户id
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginuser = ((LoginUser) authentication.getPrincipal());
//        String id = loginuser.getId().toString();
//        int state = loginuser.getState();
//        //将redis中的数据删去
//        redisTemplate.delete("login"+state+":"+id);
//
        return RespBean.success("注销成功！");
    }


}
