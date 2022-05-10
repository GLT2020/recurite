package com.glt.server.serviceImpl;

import com.glt.server.pojo.Company;
import com.glt.server.mapper.CompanyMapper;
import com.glt.server.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业用户 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-05-01
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
