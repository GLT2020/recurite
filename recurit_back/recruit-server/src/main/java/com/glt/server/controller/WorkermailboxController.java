package com.glt.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glt.server.pojo.Companymailbox;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Workermailbox;
import com.glt.server.service.WorkermailboxService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 工人邮箱 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/workermailbox")
public class WorkermailboxController {

    @Autowired
    private WorkermailboxService workermailboxService;


//    ==========================公司使用
    @ApiOperation("公司获取收件箱")
    @GetMapping("/company/{currentPage}/{size}/{cid}")
    public RespPageBean getCompanyMsg(@PathVariable Long cid,@PathVariable Integer currentPage,@PathVariable Integer size){
        return workermailboxService.getCompanyMsg(cid,currentPage,size);
    }

    @ApiOperation("公司获取有多少未读消息")
    @GetMapping("/notReadCount/{cid}")
    public Long getnotReadCount(@PathVariable Integer cid){
        QueryWrapper<Workermailbox> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver",cid).eq("state",0).eq("comdflag",1);
        long count = workermailboxService.count(queryWrapper);

        return count;
    }

//    @ApiOperation("公司获取所有未读信息")
//    @GetMapping("/notReadList/{cid}")
//    public List<Workermailbox> getAllNotReadList(@PathVariable Integer cid){
//        QueryWrapper<Workermailbox> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("receiver",cid).eq("state",0);
//        return workermailboxService.list();
//    }

    @ApiOperation("企业读取邮件,设置状态为已读")
    @PutMapping("/company/checkMsg")
    public void checkMsg(@RequestBody Workermailbox workermailbox){
        workermailbox.setState(1);
        boolean b = workermailboxService.updateById(workermailbox);

    }

    @ApiOperation("企业删除收件箱信息")
    @DeleteMapping("/deleteReceivedMsg")
    public RespBean deleteReceivedMsg(@RequestBody Workermailbox workermailbox){
        System.out.println("工人删除收件箱的消息"+workermailbox);
        //判断公司方是否删除
        if(workermailbox.getComdflag() == 1){
            //对方还未删除,只修改删除状态为0
            workermailbox.setComdflag(0);
            boolean b = workermailboxService.updateById(workermailbox);
            if(b){
                return RespBean.success("删除成功");
            }
        }
        else{
            //对方也已经删除，这时直接删除信息
            boolean b = workermailboxService.removeById(workermailbox);
            if(b){
                return RespBean.success("删除成功");
            }
        }
        return RespBean.error("删除失败，请重试");
    }

//    =====================工人使用
    @ApiOperation("写信给企业")
    @PostMapping("/send")
    public RespBean sendMsg(@RequestBody Workermailbox workermailbox){
        System.out.println("发送消息给工人"+workermailbox.toString());
        return workermailboxService.sendMsg(workermailbox);
    }

    @ApiOperation("工人获取已发送消息")
    @GetMapping("/getSendedMsg/{currentPage}/{size}/{wid}")
    public RespPageBean getSendedMsg(@PathVariable Long wid,@PathVariable Integer currentPage,@PathVariable Integer size){
        System.out.println("工人id"+wid+"当前页数"+currentPage+"页数大小"+size);
        return workermailboxService.getSendedMsg(wid,currentPage,size);
    }

    @ApiOperation("工人删除发件箱信息")
    @DeleteMapping("/deleteSendedMsg")
    public RespBean deleteSendedMsg(@RequestBody Workermailbox workermailbox){
        System.out.println("工人删除发件箱的消息"+workermailbox);
        //判断公司方是否删除
        if(workermailbox.getComdflag() == 1){
            //对方还未删除,只修改删除状态为0
            workermailbox.setWorkdflag(0);
            boolean b = workermailboxService.updateById(workermailbox);
            if(b){
                return RespBean.success("删除成功");
            }
        }
        else{
            //对方也已经删除，这时直接删除信息
            boolean b = workermailboxService.removeById(workermailbox);
            if(b){
                return RespBean.success("删除成功");
            }
        }
        return RespBean.error("删除失败，请重试");
    }

}
