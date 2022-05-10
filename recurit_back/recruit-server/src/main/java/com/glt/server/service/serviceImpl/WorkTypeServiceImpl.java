package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.WorkTypeMapper;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.WorkType;
import com.glt.server.service.WorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工种类 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-12
 */
@Service
public class WorkTypeServiceImpl extends ServiceImpl<WorkTypeMapper, WorkType> implements WorkTypeService {

    @Autowired
    private WorkTypeMapper workTypeMapper;

    //设置禁用或使用
    @Override
    public RespBean updateWorkType(WorkType workType, Integer flag) {
        workType.setState(flag);
        int i = workTypeMapper.updateById(workType);
        if(i == 0){
            return RespBean.error("修改失败，请重试");
        }
        return RespBean.success("修改成功");

    }
}
