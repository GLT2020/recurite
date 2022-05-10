package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.Company;
import com.glt.server.pojo.Site;
import com.glt.server.pojo.Worker;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 工地 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
@Repository
public interface SiteMapper extends BaseMapper<Site> {
    //获取对应公司属性所有工地
    List<Site> getcompanySites(int id);

    //获取公司属下招聘中的工地
    List<Site> getcompanyRecuritSites(int id);

    //插入工地并返回id
    Long insertGetId(Site site);

    //获取工地详细信息（包含招聘信息）
    Site getSiteDetails(long id);

    //多条件分页查询工地
    IPage<Site> searchSiteByPage(Page<Site> page, Site site, LocalDate[] beginDateScope);

    Company getCompanyByCondition(long id);


    //系统管理员获取所有用户
    IPage<Site> getAllSiteByPage(Page<Site> page);

    //导出所有公司
    List<Site> exportAllsite();
}
