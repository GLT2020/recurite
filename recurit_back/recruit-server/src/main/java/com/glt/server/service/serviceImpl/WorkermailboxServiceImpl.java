package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.WorkermailboxMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.WorkermailboxService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 工人邮箱 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class WorkermailboxServiceImpl extends ServiceImpl<WorkermailboxMapper, Workermailbox> implements WorkermailboxService {

    @Autowired
    private WorkermailboxMapper workermailboxMapper;

    //公司获取收件箱
    @Override
    public RespPageBean getCompanyMsg(Long cid, Integer currentPage,Integer size) {
        Page<Workermailbox> page = new Page<>(currentPage, size, true);

        IPage<Workermailbox> siteByPage = workermailboxMapper.getCompanyMsgByPage(page,cid);
        RespPageBean respPageBean = new RespPageBean(siteByPage.getTotal(), siteByPage.getRecords());
        return respPageBean;
    }



    //工人发送消息
    @Override
    public RespBean sendMsg(Workermailbox workermailbox) {
        workermailbox.setCreateTime(LocalDate.now());
        workermailbox.setState(0);
        workermailbox.setWorkdflag(1);
        //工人发出的消息一定带有工地信息
        if(null == workermailbox.getWorktypeId() || 0 == workermailbox.getWorktypeId()){
            //没有招聘消息信息
            //如果不是回复信，信件类别改为普通
            if(workermailbox.getType() != 2){
                workermailbox.setType(0);
            }
            workermailbox.setSiteId(0L);
            workermailbox.setWorktypeId(0L);
        }
        int insert = workermailboxMapper.insert(workermailbox);
        if(insert != 0){
            return RespBean.success("发送成功");
        }
        return RespBean.error("发送失败，请重试");

    }

    //工人获取发件箱
    @Override
    public RespPageBean getSendedMsg(Long wid, Integer currentPage, Integer size) {
        Page<Workermailbox> page = new Page<>(currentPage, size, true);
        IPage<Workermailbox> workermailboxIPage = workermailboxMapper.getSendedMsg(page,wid);
        RespPageBean respPageBean = new RespPageBean(workermailboxIPage.getTotal(), workermailboxIPage.getRecords());
        System.out.println("获取到的已发送的信息"+workermailboxIPage.getRecords());
        return respPageBean;

    }
}
