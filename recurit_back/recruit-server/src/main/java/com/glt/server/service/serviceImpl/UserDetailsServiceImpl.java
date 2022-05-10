package com.glt.server.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glt.server.mapper.AdminMapper;
import com.glt.server.mapper.CompanyMapper;
import com.glt.server.mapper.WorkerMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.AdminService;
import com.glt.server.service.CompanyService;
import com.glt.server.service.WorkerService;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WorkerService workerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NameHelper nameHelper = JSON.parseObject(username, NameHelper.class);
        System.out.println("登录Loaduserbyusernaem中的电话号:"+nameHelper.getPhone());

        if(nameHelper.getState() == 0){
            LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
             queryWrapper.eq(Admin::getPhone, nameHelper.getPhone());
            Admin admin = adminMapper.selectOne(queryWrapper);
            //没有查到用户就抛异常
            if(Objects.isNull(admin)){
//                throw  new RuntimeException("用户名或密码错误");
                return null;
            }
            //TODO 查询用户权限信息
            // List<String> list = new ArrayList<>(Arrays.asList("user","test"));
            admin.setRoles(adminService.getRoles(admin.getId()));

            //把数据封装成userdetail
            return new LoginUser(admin);
        }

        else if(nameHelper.getState() == 1){ //企业用户登录
            LambdaQueryWrapper<Company> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Company::getPhone, nameHelper.getPhone());
            Company company = companyMapper.selectOne(queryWrapper);
            //没有查到用户就抛异常
            if(Objects.isNull(company)){
//                throw  new RuntimeException("用户名或密码错误");
                return null;
            }

            //TODO 查询用户权限信息
            // List<String> list = new ArrayList<>(Arrays.asList("user","test"));
            company.setRoles(companyService.getRoles(company.getId()));

            //把数据封装成userdetail
            return new LoginUser(company);
        }else{
            //工人用户登录
            LambdaQueryWrapper<Worker> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Worker::getPhone, nameHelper.getPhone());
            Worker worker = workerMapper.selectOne(queryWrapper);
            //没有查到用户就抛异常
            if(Objects.isNull(worker)){
//                throw  new RuntimeException("用户名或密码错误");
                return  null;
            }
            //TODO 查询用户权限信息
            // List<String> list = new ArrayList<>(Arrays.asList("user","test"));
            worker.setRoles(workerService.getRoles(worker.getId()));

            //把数据封装成userdetail
            return new LoginUser(worker);
        }

    }
}
