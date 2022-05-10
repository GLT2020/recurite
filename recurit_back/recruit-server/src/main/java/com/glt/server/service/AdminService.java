package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.Admin;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Role;

import java.util.List;

/**
 * <p>
 * 管理员用户 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface AdminService extends IService<Admin> {

    List<Role> getRoles(Long id);

    //获取所有系统用户
    RespPageBean searchAllAdminByPage(Integer currentPage, Integer size);

    //添加用户
    RespBean addAdmin(Admin admin, Long rid);

    //删除用户
    RespBean deleteAdmin(Admin admin);
}
