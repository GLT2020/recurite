package com.glt.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glt.server.pojo.Admin;
import com.glt.server.pojo.AdminRole;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.service.AdminRoleService;
import com.glt.server.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 * 管理员用户 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("根据id获取系统用户信息")
    @GetMapping("/{id}")
    public Admin GetAdmin(@PathVariable long id){
        return adminService.getById(id);
    }


    @ApiOperation("分页获取所有系统用户")
    @GetMapping("/all/{currentPage}/{size}")
    public RespPageBean searchAllAdminByPage(@PathVariable Integer currentPage, @PathVariable Integer size){
        //设置两个默认值

        if(currentPage == null) currentPage = 1;
        if(size == null) size =10;

        return adminService.searchAllAdminByPage(currentPage,size);
    }

    @ApiOperation("添加系统用户")
    @PostMapping("/add/{rid}")
    public RespBean addAdmin(@RequestBody Admin admin,@PathVariable Long rid){
        return adminService.addAdmin(admin,rid);
    }

    @ApiOperation("修改系统用户")
    @PutMapping("/update")
    public RespBean updateAdmin(@RequestBody Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUpdateTime(LocalDate.now());
        boolean b = adminService.updateById(admin);
        if(b){
            return RespBean.success("修改成功");
        }

        return RespBean.error("修改失败，请重试");
    }

    @ApiOperation("删除系统用户")
    @DeleteMapping("/delete")
    public RespBean deleteAdmin(@RequestBody Admin admin){
        return adminService.deleteAdmin(admin);
    }
}
