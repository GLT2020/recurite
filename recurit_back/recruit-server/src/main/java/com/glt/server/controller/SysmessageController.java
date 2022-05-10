package com.glt.server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.Company;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Sysmessage;
import com.glt.server.service.SysmessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/sysmessage")
public class SysmessageController {

    @Autowired
    private SysmessageService sysmessageService;

    @ApiOperation("获取所有公告")
    @GetMapping("/{currentPage}/{size}")
    public RespPageBean getallMsg(@PathVariable Integer currentPage, @PathVariable Integer size){
        Page<Sysmessage> page = new Page<>(currentPage, size, true);
        Page<Sysmessage> page1 = sysmessageService.page(page);
        RespPageBean respPageBean = new RespPageBean(page1.getTotal(), page1.getRecords());
        return respPageBean;
    }

    @ApiOperation("添加公告")
    @PostMapping("/")
    public RespBean insertMsg(@RequestBody Sysmessage sysmessage){
        sysmessage.setCreateTime(LocalDate.now());
        boolean save = sysmessageService.save(sysmessage);
        if(save){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation("修改公告")
    @PutMapping("/")
    public RespBean updateMsg(@RequestBody Sysmessage sysmessage){
        sysmessage.setCreateTime(LocalDate.now());
        boolean save = sysmessageService.updateById(sysmessage);
        if(save){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation("删除公告")
    @DeleteMapping("/{id}")
    public RespBean deleteMsg(@PathVariable Integer id){
        boolean b = sysmessageService.removeById(id);
        if(b){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

//    工人、企业获取所有公告
    @ApiOperation("工人、企业获取所有公告")
    @GetMapping("/getAll")
    public List<Sysmessage> getAll(){
        return sysmessageService.list();
    }
}
