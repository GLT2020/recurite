package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.Admin;
import com.glt.server.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 管理员用户 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
//    获取系统用户角色
    List<Role> getRoles(Long id);

    //分页获取所有系统用户
    IPage<Admin> searchAllAdminByPage(Page<Admin> page);
}
