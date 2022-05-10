package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.RecuritMessageMapper;
import com.glt.server.mapper.SiteMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 工地 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteService {

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private RecuritMessageMapper recuritMessageMapper;

    //获取对应公司属性所有工地
    @Override
    public List<Site> getcompanySites(int id) {
        return siteMapper.getcompanySites(id);
    }


    //获得公司属下招聘中的工地
    @Override
    public List<Site> getcompanyrecuritySites(int id) {
        return siteMapper.getcompanyRecuritSites(id);
    }



    //修改工地信息
    @Override
    public RespBean updateSite(Site site) {
        if(site.getRecuritState() == 0 || site.getSiteState() == 0){
            //如果工地不再招聘或设置为完工，旗下所有招聘信息都要设置为不招聘
            if(site.getSiteState() == 0){
                //说明此时设置为完工,
                //1.本身招聘设置为不再招聘，并设置完工时间
                site.setCompleteTime(LocalDate.now());
                site.setRecuritState(0);
            }
            // 2.将其下的所有招聘信息设置为不再招聘
            List<RecuritMessage> list = recuritMessageMapper.getSiteAllRecruitMsg(site.getId());
//            System.out.println("返回的list->>>>>>>>>>>>>>>>>>>>>>>>>>>>"+list.get(0));
            if(list.size() != 0 && null != list.get(0)){
                list.forEach(item->{
                    item.setState(0);
                    recuritMessageMapper.updateById(item);
                });
            }

        }

        int i = siteMapper.updateById(site);
        if(i != 0){
            return RespBean.success("更新成功");
        }
        return RespBean.success("更新成功");
    }



    //多条件分页查询工地
    @Override
    public RespPageBean searchSiteByPage(Site site, Integer currentPage, Integer size, LocalDate[] beginDateScope) {
        //使用Mybatis-plus的分页对象，实现自动分页
        Page<Site> page = new Page<>(currentPage, size, true);
        page.setOptimizeCountSql(false);
        //按条件查询工地列表
        IPage<Site> siteByPage = siteMapper.searchSiteByPage(page,site,beginDateScope);
        //自主封装的返回给前端对象数据
        RespPageBean respPageBean = new RespPageBean(siteByPage.getTotal(), siteByPage.getRecords());
        return respPageBean;
    }

    //系统管理员分页获取所有工地信息
    @Override
    public RespPageBean getAllSite(Integer currentPage, Integer size) {
        Page<Site> page = new Page<>(currentPage, size, true);
        page.setOptimizeCountSql(false);
        IPage<Site> siteByPage = siteMapper.getAllSiteByPage(page);
        RespPageBean respPageBean = new RespPageBean(siteByPage.getTotal(), siteByPage.getRecords());
        return respPageBean;
    }

    //导出工地
    @Override
    public List<Site> exportAllSite() {
        return siteMapper.exportAllsite();
    }

}
