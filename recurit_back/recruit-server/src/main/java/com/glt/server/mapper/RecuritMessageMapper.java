package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.RecuritMessage;
import com.glt.server.pojo.Site;
import com.glt.server.pojo.Worker;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 招聘信息 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
public interface RecuritMessageMapper extends BaseMapper<RecuritMessage> {


    //添加招聘信息并返回id
    Long addRecuritMsg(RecuritMessage recuritMessage);

    //获取招聘信息的应聘工人
    List<Worker> getWorkerMsgBySid(Long id);

    //根据工地ID获取工地的所有招聘信息
    List<RecuritMessage> getSiteAllRecruitMsg(Long sid);

    //分页查询招聘信息（返回信息包含工地）
    IPage<RecuritMessage> searchRecuritMsgByPage(Page<Site> page, Integer stypeId,Integer wtypeId );

    //分页查询招聘信息，同时筛选工地信息（返回信息包含工地）
    IPage<RecuritMessage> searchRecuritMsgWithAddressByPage(Page<Site> page, Integer stypeId,Integer wtypeId , String address);

    //工人获取应聘的应聘消息
    IPage<RecuritMessage> searchApplyRMsgByPage(Page<RecuritMessage> page, Integer id);
}
