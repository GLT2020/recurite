package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.Workermailbox;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 工人邮箱 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
public interface WorkermailboxMapper extends BaseMapper<Workermailbox> {
    //公司获取收件箱
    IPage<Workermailbox> getCompanyMsgByPage(Page<Workermailbox> page, Long cid);

    //工人获取发件箱
    IPage<Workermailbox> getSendedMsg(Page<Workermailbox> page, Long wid);
}
