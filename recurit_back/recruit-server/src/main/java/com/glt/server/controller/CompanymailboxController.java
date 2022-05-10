package com.glt.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glt.server.pojo.Companymailbox;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Workermailbox;
import com.glt.server.service.CompanymailboxService;
import com.glt.server.service.WorkermailboxService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 企业邮箱 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/companymailbox")
public class CompanymailboxController {

//    接收用的
    @Autowired
    private WorkermailboxService workermailboxService;

    // 写信用的的
    @Autowired
    private CompanymailboxService companymailboxService;



//=============================公司使用
    @ApiOperation("写信给工人")
    @PostMapping("/send")
    public RespBean sendMsg(@RequestBody Companymailbox companymailbox){
        System.out.println("发送消息给工人"+companymailbox.toString());
        return companymailboxService.sendMsg(companymailbox);
    }


    @ApiOperation("公司获取已发送消息")
    @GetMapping("/getSendedMsg/{currentPage}/{size}/{cid}")
    public RespPageBean getSendedMsg(@PathVariable Long cid,@PathVariable Integer currentPage,@PathVariable Integer size){
        System.out.println("公司id"+cid+"当前页数"+currentPage+"页数大小"+size);
        return companymailboxService.getSendedMsg(cid,currentPage,size);
    }

    @ApiOperation("公司发送箱移除消息")
    @DeleteMapping("/deleteSendedMsg")
    public RespBean deleteSendedMsg(@RequestBody Companymailbox companymailbox){
        //判断工人方是否删除信息
        if(companymailbox.getWorkdflag() == 1){
            //此时工人还未删除
            companymailbox.setComdflag(0);
            boolean update = companymailboxService.updateById(companymailbox);
            if(update){
                return RespBean.success("删除成功");
            }
        }
        else{
            //对方也已经删除，这里直接删除信息
            boolean update = companymailboxService.removeById(companymailbox);
            if(update){
                return RespBean.success("删除成功");
            }
        }
        return RespBean.error("删除失败，请重试");

    }






//    =====================工人使用
    @ApiOperation("工人获取有多少未读消息")
    @GetMapping("/notReadCount/{wid}")
    public Long getnotReadCount(@PathVariable Integer wid){
        QueryWrapper<Companymailbox> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver",wid).eq("state",0).eq("workdflag",1);
        long count = companymailboxService.count(queryWrapper);

        return count;
    }

    @ApiOperation("工人获取收件箱")
    @GetMapping("/worker/{currentPage}/{size}/{wid}")
    public RespPageBean getWorkerMsg(@PathVariable Long wid,@PathVariable Integer currentPage,@PathVariable Integer size){
        return companymailboxService.getWorkerMsg(wid,currentPage,size);
    }

    @ApiOperation("工人读取邮件,设置状态为已读")
    @PutMapping("/worker/checkMsg")
    public void checkMsg(@RequestBody Companymailbox companymailbox){
        companymailbox.setState(1);
        boolean b = companymailboxService.updateById(companymailbox);

    }

    @ApiOperation("工人删除收件箱信息")
    @DeleteMapping("/deleteReceivedMsg")
    public RespBean deleteReceivedMsg(@RequestBody Companymailbox companymailbox){
        System.out.println("工人删除收件箱的消息"+companymailbox);
        //判断公司方是否删除
        if(companymailbox.getComdflag() == 1){
            //对方还未删除,只修改删除状态为0
            companymailbox.setWorkdflag(0);
            boolean b = companymailboxService.updateById(companymailbox);
            if(b){
                return RespBean.success("删除成功");
            }
        }
        else{
            //对方也已经删除，这时直接删除信息
            boolean b = companymailboxService.removeById(companymailbox);
            if(b){
                return RespBean.success("删除成功");
            }
        }
        return RespBean.error("删除失败，请重试");
    }

}
