package com.glt.server.serviceImpl;

import com.glt.server.pojo.AdminRole;
import com.glt.server.mapper.AdminRoleMapper;
import com.glt.server.service.AdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员-角色表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

}
