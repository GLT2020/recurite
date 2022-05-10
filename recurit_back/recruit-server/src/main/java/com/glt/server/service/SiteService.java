package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Site;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 工地 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface SiteService extends IService<Site> {

    //获取对应公司属性所有工地
    List<Site> getcompanySites(int id);

    //多条件分页查询工地
    RespPageBean searchSiteByPage(Site site, Integer currentPage, Integer size, LocalDate[] beginDateScope);

    //更修工地信息
    RespBean updateSite(Site site);

    //获得公司属下招聘中的工地
    List<Site> getcompanyrecuritySites(int id);

    //获取所有工地信息
    RespPageBean getAllSite(Integer currentPage, Integer size);

    //导出工地
    List<Site> exportAllSite();
}
