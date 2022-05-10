package com.glt.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.WorkType;
import com.glt.server.service.WorkTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 工种类 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-12
 */
@RestController
@RequestMapping("/worktype")
public class WorkTypeController {

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("获取所有工种种类")
    @GetMapping("/")
    public List<WorkType> getAllWorkType(){
        List<WorkType> list = (List<WorkType>) redisTemplate.opsForValue().get("workType");
//        List<WorkType> list = new ArrayList<>();
        if(list == null || list.size() == 0){
            QueryWrapper<WorkType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("state",1);
            list = workTypeService.list(queryWrapper);
            redisTemplate.opsForValue().set("workType",list, 30, TimeUnit.MINUTES);
        }

        return list;
    }


    @ApiOperation("获取系统用户分页工种种类")
    @GetMapping("/admin/{currentPage}/{size}")
    public RespPageBean getAdminWorkType(@PathVariable Integer currentPage,@PathVariable Integer size){
        Page<WorkType> workTypePage = new Page<>(currentPage, size);
        Page<WorkType> page = workTypeService.page(workTypePage);
        RespPageBean respPageBean = new RespPageBean(page.getTotal(), page.getRecords());
        return respPageBean;
    }

    @ApiOperation("设置启用或禁用")
    @PutMapping("/admin/user/{flag}")
    public RespBean updateWorkType(@RequestBody WorkType workType, @PathVariable Integer flag){
        return workTypeService.updateWorkType(workType,flag);
    }

    @ApiOperation("添加工种")
    @PostMapping("/admin/addWorkType")
    public RespBean addWorkType(@RequestBody WorkType workType){
        QueryWrapper<WorkType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",workType.getName());
        WorkType one = workTypeService.getOne(queryWrapper);
        //当工种名没有重复的时候可以添加
        if(null == one || null == one.getName()) {
            boolean save = workTypeService.save(workType);
            if(save){
                return RespBean.success("添加成功");
            }
        }


        return RespBean.error("添加失败，该工种可能已存在，请重试");
    }
}
