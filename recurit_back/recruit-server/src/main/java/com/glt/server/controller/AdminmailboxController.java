package com.glt.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.*;
import com.glt.server.service.AdminmailboxService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/adminmailbox")
public class AdminmailboxController {

    @Autowired
    private AdminmailboxService adminmailboxService;

    @ApiOperation("获取所有的反馈")
    @GetMapping("/{currentPage}/{size}")
    public RespPageBean getAllMail(@PathVariable Integer currentPage, @PathVariable Integer size){
        Page<Adminmailbox> page = new Page<>(currentPage, size, true);
        QueryWrapper<Adminmailbox> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("state",0);
        Page<Adminmailbox> page1 = adminmailboxService.page(page,queryWrapper);
        RespPageBean respPageBean = new RespPageBean(page1.getTotal(), page1.getRecords());
        return respPageBean;
    }

    @ApiOperation("删除反馈")
    @DeleteMapping("/{id}")
    public RespBean deleteMsg(@PathVariable Integer id){
        boolean b = adminmailboxService.removeById(id);
        if(b){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation("设置读取反馈")
    @PutMapping("/checkMsg")
    public void checkMsg(@RequestBody Adminmailbox adminmailbox){
        adminmailbox.setState(1);
        boolean b = adminmailboxService.updateById(adminmailbox);

    }

//    工人企业发出反馈
    @ApiOperation("工人企业发出反馈")
    @PostMapping("/send")
    public RespBean sendMsg(@RequestBody Adminmailbox adminmailbox){
        adminmailbox.setCreateTime(LocalDate.now());
        adminmailbox.setState(0);
        boolean save = adminmailboxService.save(adminmailbox);
        if(save){
            return RespBean.success("反馈成功");
        }
        return RespBean.error("反馈失败，请重试");
    }



}
