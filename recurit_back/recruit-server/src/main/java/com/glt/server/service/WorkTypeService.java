package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.WorkType;

/**
 * <p>
 * 工种类 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-12
 */
public interface WorkTypeService extends IService<WorkType> {
    //设置禁用或使用
    RespBean updateWorkType(WorkType workType, Integer flag);
}
