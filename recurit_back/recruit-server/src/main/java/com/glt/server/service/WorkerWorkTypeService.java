package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.WorkType;
import com.glt.server.pojo.WorkerWorkType;

import java.util.List;

/**
 * <p>
 * 工人-工种表 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface WorkerWorkTypeService extends IService<WorkerWorkType> {

    //添加工人与工种关系
    void add(Long id, List<Integer> list);
}
