package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.Companymailbox;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;

/**
 * <p>
 * 企业邮箱 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface CompanymailboxService extends IService<Companymailbox> {

    //写信给工人
    RespBean sendMsg(Companymailbox companymailbox);

    //获取公司发送出的邮件
    RespPageBean getSendedMsg(Long cid, Integer currentPage, Integer size);

    //工人获取收件箱
    RespPageBean getWorkerMsg(Long wid, Integer currentPage, Integer size);
}
