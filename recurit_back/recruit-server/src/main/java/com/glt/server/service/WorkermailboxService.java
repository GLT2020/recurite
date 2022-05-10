package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Workermailbox;

import java.util.List;

/**
 * <p>
 * 工人邮箱 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface WorkermailboxService extends IService<Workermailbox> {

    //公司获取收件箱
    RespPageBean getCompanyMsg(Long cid,Integer currentPage,Integer size);

    //工人发送消息
    RespBean sendMsg(Workermailbox workermailbox);

    //工人获取发件箱
    RespPageBean getSendedMsg(Long wid, Integer currentPage, Integer size);
}
