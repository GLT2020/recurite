package com.glt.server.mapper;

import com.glt.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理员用户 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-12
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}
