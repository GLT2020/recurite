package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.CompanySiteMapper;
import com.glt.server.pojo.CompanySite;
import com.glt.server.service.CompanySiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业-工地表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class CompanySiteServiceImpl extends ServiceImpl<CompanySiteMapper, CompanySite> implements CompanySiteService {

    @Autowired
    private CompanySiteMapper companySiteMapper;


}
