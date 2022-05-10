package com.glt.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glt.server.mapper.RecuritMessageMapper;
import com.glt.server.mapper.SiteRecuritMessageMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.RecuritMessageService;
import com.glt.server.service.RecuritMessageWorkerService;
import com.glt.server.service.SiteRecuritMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 招聘信息 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/recuritMessage")
public class RecuritMessageController {

    @Autowired
    private RecuritMessageService recuritMessageService;

    @Autowired
    private RecuritMessageMapper recuritMessageMapper;


    @Autowired
    private SiteRecuritMessageService siteRecuritMessageService;

    @Autowired
    private SiteRecuritMessageMapper siteRecuritMessageMapper;

    @Autowired
    private RecuritMessageWorkerService recuritMessageWorkerService;

    @ApiOperation("获取招聘信息的应聘工人")
    @GetMapping("/{id}")
    public List<Worker> getAllworkerMsg(@PathVariable Long id){
        List<Worker> list = recuritMessageMapper.getWorkerMsgBySid(id);
//        System.out.println(list.get(0).toString());
        return list;
    }

    @ApiOperation("获取工地所有的招聘信息招聘信息")
    @GetMapping("/site/{sid}")
    public List<RecuritMessage> getSiteAllRecruitMsg(@PathVariable Long sid){
        List<RecuritMessage> list = recuritMessageMapper.getSiteAllRecruitMsg(sid);
//        System.out.println(list.get(0).toString());
        return list;
    }

    @ApiOperation("添加招聘信息")
    @PostMapping("/{sid}")
    @PreAuthorize("hasRole('company')")
    public RespBean addRecruitMessage(@RequestBody RecuritMessage recuritMessage,@PathVariable Long sid){
        recuritMessage.setCreateTime(LocalDate.now());
        recuritMessageMapper.addRecuritMsg(recuritMessage);
        Long rid = recuritMessage.getId();
        boolean save = siteRecuritMessageService.save(new SiteRecuritMessage(sid, rid));
        if(save){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @ApiOperation("修改招聘信息")
    @PutMapping("/")
    @PreAuthorize("hasRole('company')")
    public RespBean updateRecruitMessage(@RequestBody RecuritMessage recuritMessage){
        int i = recuritMessageMapper.updateById(recuritMessage);

        if(i!=0){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }



    @ApiOperation("删除招聘信息")
    @DeleteMapping("/{rid}")
    public RespBean deleteRecruitMessage(@PathVariable Long rid){
        System.out.println("删除招聘信息的id"+rid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rid",rid);
        int i1 = siteRecuritMessageMapper.deleteByMap(map);
        boolean b = recuritMessageWorkerService.removeByMap(map);
        int i = recuritMessageMapper.deleteById(rid);

        if(i!=0 && i1!=0 && b==true){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }



//   ----------------------------------------------- 工人使用

//    应聘
    @ApiOperation("应聘招聘信息")
    @PostMapping("/apply/{wid}/{rid}")
    public RespBean applyRecruitMessage(@PathVariable Long wid,@PathVariable Long rid){
        return recuritMessageWorkerService.apply(wid,rid);

    }

//    多条件查询招聘信息
    @ApiOperation("多条件查询招聘信息")
    @GetMapping(value = "/search/{currentPage}/{size}")
    public RespPageBean searchByPage(@PathVariable Integer currentPage, @PathVariable Integer size, @RequestParam(defaultValue = "0") Integer stypeId, @RequestParam(defaultValue = "0") Integer wtypeId, @RequestParam(defaultValue = "null") String address){

        if(currentPage == null) currentPage = 1;
        if(size == null) size =5;
        System.out.println("分页查询招聘信息的三个数据->>>>>>>>"+stypeId+">>>"+wtypeId+">>>>>"+address);
        return recuritMessageService.searchRecuritMsgByPage(currentPage,size,stypeId,wtypeId ,address);
    }

//    获取工人应聘的应聘消息
    @ApiOperation("获取工人应聘的应聘消息")
    @GetMapping("/getApply/{currentPage}/{size}/{id}")
    public RespPageBean getApply(@PathVariable Integer id,@PathVariable Integer currentPage,@PathVariable Integer size){
        if(currentPage == null) currentPage = 1;
        if(size == null) size =5;
        return recuritMessageService.getApply(id,currentPage,size);
    }

//    工人取消应聘
    @ApiOperation("工人取消应聘")
    @GetMapping("/cancelApply/{wid}/{rid}")
    public RespBean cancelApply(@PathVariable Long rid,@PathVariable Long wid){
        QueryWrapper<RecuritMessageWorker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workerId",wid).eq("rid",rid);
        boolean remove = recuritMessageWorkerService.remove(queryWrapper);
        if(remove){
            return RespBean.success("取消成功");
        }
        return RespBean.error("取消失败，请重试");
    }
}
