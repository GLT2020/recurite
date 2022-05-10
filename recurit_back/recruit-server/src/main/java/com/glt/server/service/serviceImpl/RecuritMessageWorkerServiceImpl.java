package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.RecuritMessageWorkerMapper;
import com.glt.server.pojo.RecuritMessageWorker;
import com.glt.server.pojo.RespBean;
import com.glt.server.service.RecuritMessageWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 招聘信息-工人表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class RecuritMessageWorkerServiceImpl extends ServiceImpl<RecuritMessageWorkerMapper, RecuritMessageWorker> implements RecuritMessageWorkerService {

    @Autowired
    private RecuritMessageWorkerMapper recuritMessageWorkerMapper;

    //添加应聘信息
    @Override
    public RespBean apply(Long wid, Long rid) {
        //需要判断该工人是否已经应聘了这个招聘信息
        QueryWrapper<RecuritMessageWorker> wrapper = new QueryWrapper<>();
        wrapper.eq("workerId",wid);
        wrapper.eq("rid",rid);
        Long count = recuritMessageWorkerMapper.selectCount(wrapper);
        System.out.println("查询是否有应聘过->>>>>>"+count);
        if(count == 0){
            //说明没应聘过
            RecuritMessageWorker recuritMessageWorker = new RecuritMessageWorker(wid, rid);
            recuritMessageWorker.setCreateTime(LocalDate.now());
            int insert = recuritMessageWorkerMapper.insert(recuritMessageWorker);
            if(insert!=0){
                return RespBean.success("应聘成功");
            }else{
                return RespBean.error("应聘失败，请重试");
            }

        }
        return RespBean.error("应聘失败，您已应聘！");
    }
}
