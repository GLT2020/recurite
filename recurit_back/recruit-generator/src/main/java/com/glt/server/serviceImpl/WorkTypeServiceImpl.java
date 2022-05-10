package com.glt.server.serviceImpl;

import com.glt.server.pojo.WorkType;
import com.glt.server.mapper.WorkTypeMapper;
import com.glt.server.service.WorkTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工种类 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-17
 */
@Service
public class WorkTypeServiceImpl extends ServiceImpl<WorkTypeMapper, WorkType> implements WorkTypeService {

}
