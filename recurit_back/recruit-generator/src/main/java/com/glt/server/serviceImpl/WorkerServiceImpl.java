package com.glt.server.serviceImpl;

import com.glt.server.pojo.Worker;
import com.glt.server.mapper.WorkerMapper;
import com.glt.server.service.WorkerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工人用户 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-14
 */
@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerMapper, Worker> implements WorkerService {

}
