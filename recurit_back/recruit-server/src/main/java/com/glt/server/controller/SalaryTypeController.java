package com.glt.server.controller;


import com.glt.server.pojo.SalaryType;
import com.glt.server.pojo.WorkType;
import com.glt.server.service.SalaryTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 薪资种类 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/salaryType")
public class SalaryTypeController {

    @Autowired
    private SalaryTypeService salaryTypeService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("获取所有工资种类")
    @GetMapping("/")
    public List<SalaryType> getAllSalray(){
        List<SalaryType> list = (List<SalaryType>) redisTemplate.opsForValue().get("salaryType");

        if(list == null){
            list = salaryTypeService.list();
            redisTemplate.opsForValue().set("salaryType",list, 30, TimeUnit.MINUTES);
        }

        return list;
    }
}
