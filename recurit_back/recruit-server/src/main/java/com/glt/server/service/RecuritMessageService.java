package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.RecuritMessage;
import com.glt.server.pojo.RespPageBean;

/**
 * <p>
 * 招聘信息 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface RecuritMessageService extends IService<RecuritMessage> {

    //多条件分页查询招聘信息
    RespPageBean searchRecuritMsgByPage( Integer currentPage, Integer size,Integer stypeId,Integer wtypeId, String address);

    //工人获取应聘的应聘消息
    RespPageBean getApply(Integer id,Integer currentPage,Integer size);
}
