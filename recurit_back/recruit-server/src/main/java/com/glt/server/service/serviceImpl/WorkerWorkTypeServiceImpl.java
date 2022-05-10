package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.WorkerWorkTypeMapper;
import com.glt.server.pojo.WorkType;
import com.glt.server.pojo.WorkerWorkType;
import com.glt.server.service.WorkerWorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private WorkerWorkTypeMapper workerWorkTypeMapper;

    //添加工人与工种关系
    @Override
    public void add(Long id, List<Integer> list) {
        workerWorkTypeMapper.add(id,list);
    }
}
