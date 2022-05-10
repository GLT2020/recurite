package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.RecuritMessageMapper;
import com.glt.server.pojo.RecuritMessage;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Site;
import com.glt.server.service.RecuritMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 招聘信息 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class RecuritMessageServiceImpl extends ServiceImpl<RecuritMessageMapper, RecuritMessage> implements RecuritMessageService {

    @Autowired
    private RecuritMessageMapper recuritMessageMapper;

    //    多条件查询招聘信息（返回附带工地信息）
    @Override
    public RespPageBean searchRecuritMsgByPage( Integer currentPage, Integer size,Integer stypeId,Integer wtypeId ,String address) {
        Page<Site> page = new Page<>(currentPage, size, true);
        page.setOptimizeCountSql(false);
        IPage<RecuritMessage> recuritMsgByPage;

        if(address.equals("null") || "".equals(address)){
            System.out.println("分页搜索招聘信息不带address条件");
            recuritMsgByPage =  recuritMessageMapper.searchRecuritMsgByPage(page,stypeId,wtypeId);
        }
        else{
            recuritMsgByPage = recuritMessageMapper.searchRecuritMsgWithAddressByPage(page,stypeId,wtypeId,address);
        }

        RespPageBean respPageBean = new RespPageBean(recuritMsgByPage.getTotal(), recuritMsgByPage.getRecords());
        return respPageBean;
    }

    //工人获取应聘的应聘消息
    @Override
    public RespPageBean getApply(Integer id,Integer currentPage,Integer size) {
        Page<RecuritMessage> page = new Page<>(currentPage, size, true);
        page.setOptimizeCountSql(false);
        IPage<RecuritMessage> rMsgByPage = recuritMessageMapper.searchApplyRMsgByPage(page,id);
        RespPageBean respPageBean = new RespPageBean(rMsgByPage.getTotal(), rMsgByPage.getRecords());
        return respPageBean;

    }
}
