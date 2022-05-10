package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.MenuMapper;
import com.glt.server.pojo.Menu;
import com.glt.server.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
