package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.MenuRoleMapper;
import com.glt.server.pojo.MenuRole;
import com.glt.server.service.MenuRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限-角色表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {

}
