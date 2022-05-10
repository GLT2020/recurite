package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.RecuritMessageWorker;
import com.glt.server.pojo.RespBean;

/**
 * <p>
 * 招聘信息-工人表 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface RecuritMessageWorkerService extends IService<RecuritMessageWorker> {

    //添加应聘信息
    RespBean apply(Long wid, Long rid);
}
