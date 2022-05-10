package com.glt.server.serviceImpl;

import com.glt.server.pojo.Site;
import com.glt.server.mapper.SiteMapper;
import com.glt.server.service.SiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工地 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-20
 */
@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteService {

}
