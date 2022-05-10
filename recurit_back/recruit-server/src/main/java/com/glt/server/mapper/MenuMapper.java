package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glt.server.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}
