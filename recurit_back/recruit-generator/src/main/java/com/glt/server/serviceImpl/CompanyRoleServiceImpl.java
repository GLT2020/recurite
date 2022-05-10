package com.glt.server.serviceImpl;

import com.glt.server.pojo.CompanyRole;
import com.glt.server.mapper.CompanyRoleMapper;
import com.glt.server.service.CompanyRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业-角色表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class CompanyRoleServiceImpl extends ServiceImpl<CompanyRoleMapper, CompanyRole> implements CompanyRoleService {

}
