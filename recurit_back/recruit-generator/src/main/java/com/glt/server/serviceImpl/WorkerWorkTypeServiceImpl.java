package com.glt.server.serviceImpl;

import com.glt.server.pojo.WorkerWorkType;
import com.glt.server.mapper.WorkerWorkTypeMapper;
import com.glt.server.service.WorkerWorkTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工人-工种表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class WorkerWorkTypeServiceImpl extends ServiceImpl<WorkerWorkTypeMapper, WorkerWorkType> implements WorkerWorkTypeService {

}
