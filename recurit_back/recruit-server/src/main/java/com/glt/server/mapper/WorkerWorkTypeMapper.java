package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glt.server.pojo.WorkType;
import com.glt.server.pojo.WorkerWorkType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 工人-工种表 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
public interface WorkerWorkTypeMapper extends BaseMapper<WorkerWorkType> {
    //添加工人与工种关系
    void add(Long id, List<Integer> list);
}
