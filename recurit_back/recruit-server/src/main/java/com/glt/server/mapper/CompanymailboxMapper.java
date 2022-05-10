package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.Companymailbox;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 企业邮箱 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
public interface CompanymailboxMapper extends BaseMapper<Companymailbox> {

    //公司获取已发送的消息
    IPage<Companymailbox> getSendedMsg(Page<Companymailbox> page, Long cid);

    //公司删除发送箱子里的消息（删除建设置为0）
    boolean deleteSendedMsg(Companymailbox companymailbox);

    //工人获取收件箱
    IPage<Companymailbox> getWorkerMsgByPage(Page<Companymailbox> page, Long wid);
}
