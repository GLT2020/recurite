package com.glt.server.serviceImpl;

import com.glt.server.pojo.SalaryType;
import com.glt.server.mapper.SalaryTypeMapper;
import com.glt.server.service.SalaryTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 薪资种类 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class SalaryTypeServiceImpl extends ServiceImpl<SalaryTypeMapper, SalaryType> implements SalaryTypeService {

}
