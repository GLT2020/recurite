package com.glt.server.serviceImpl;

import com.glt.server.pojo.WorkerRole;
import com.glt.server.mapper.WorkerRoleMapper;
import com.glt.server.service.WorkerRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工人-角色表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-11
 */
@Service
public class WorkerRoleServiceImpl extends ServiceImpl<WorkerRoleMapper, WorkerRole> implements WorkerRoleService {

}
