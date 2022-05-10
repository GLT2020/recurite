package com.glt.server.serviceImpl;

import com.glt.server.pojo.Admin;
import com.glt.server.mapper.AdminMapper;
import com.glt.server.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员用户 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-12
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
