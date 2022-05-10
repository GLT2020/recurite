package com.glt.server.serviceImpl;

import com.glt.server.pojo.Role;
import com.glt.server.mapper.RoleMapper;
import com.glt.server.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
