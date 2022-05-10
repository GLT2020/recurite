package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.CompanymailboxMapper;
import com.glt.server.pojo.Companymailbox;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Workermailbox;
import com.glt.server.service.CompanymailboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 企业邮箱 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class CompanymailboxServiceImpl extends ServiceImpl<CompanymailboxMapper, Companymailbox> implements CompanymailboxService {

    @Autowired
    private CompanymailboxMapper companymailboxMapper;

    //公司写信给工人
    @Override
    public RespBean sendMsg(Companymailbox companymailbox) {
        companymailbox.setCreateTime(LocalDate.now());
        companymailbox.setState(0);
        companymailbox.setComdflag(1);
        if(null == companymailbox.getSiteId() || 0 == companymailbox.getSiteId()){
            //没有工地消息
            //1.信件类别改为普通
            if(companymailbox.getType() != 2){
                //不是回复信则修改
                companymailbox.setType(0);
            }

            companymailbox.setSiteId(0L);
            companymailbox.setWorktypeId(0L);
        }
        int insert = companymailboxMapper.insert(companymailbox);
        if(insert != 0){
            return RespBean.success("发送成功");
        }
        return RespBean.error("发送失败，请重试");
    }

    //获取公司发送的消息
    @Override
    public RespPageBean getSendedMsg(Long cid, Integer currentPage, Integer size) {
        Page<Companymailbox> page = new Page<>(currentPage, size, true);
        IPage<Companymailbox> companymailboxIPage = companymailboxMapper.getSendedMsg(page,cid);
        RespPageBean respPageBean = new RespPageBean(companymailboxIPage.getTotal(), companymailboxIPage.getRecords());
        System.out.println("获取到的已发送的信息"+companymailboxIPage.getRecords());
        return respPageBean;


    }

    //工人获取收件箱
    @Override
    public RespPageBean getWorkerMsg(Long wid, Integer currentPage, Integer size) {
        Page<Companymailbox> page = new Page<>(currentPage, size, true);

        IPage<Companymailbox> siteByPage = companymailboxMapper.getWorkerMsgByPage(page,wid);
        RespPageBean respPageBean = new RespPageBean(siteByPage.getTotal(), siteByPage.getRecords());
        return respPageBean;

    }


}
