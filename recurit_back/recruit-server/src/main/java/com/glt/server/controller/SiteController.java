package com.glt.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.glt.server.mapper.RecuritMessageMapper;
import com.glt.server.mapper.SiteMapper;
import com.glt.server.mapper.SiteRecuritMessageMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.CompanySiteService;
import com.glt.server.service.RecuritMessageService;
import com.glt.server.service.SiteRecuritMessageService;
import com.glt.server.service.SiteService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 工地 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private CompanySiteService companySiteService;

    @Autowired
    private RecuritMessageMapper recuritMessageMapper;

    @Autowired
    private SiteRecuritMessageService siteRecuritMessageService;


    @ApiOperation("根据id获取工地详细信息")
    @GetMapping("/{id}")
    public Site getSiteDetails(@PathVariable long id){

        return siteMapper.getSiteDetails(id);
    }


    @ApiOperation("修改工地信息")
    @PutMapping("/")
    public RespBean updateSiteDetails(@RequestBody Site site){
//        System.out.println("修改工地信息->>>>>>>>>>>>"+site);
        return  siteService.updateSite(site);


    }

    @ApiOperation("假删除工地信息")
    @PutMapping("/deleteSite")
    public RespBean deleteSite(@RequestBody Site site){
        site.setDflag(0);
        System.out.println("假删除的工地消息"+site);
        boolean b = siteService.updateById(site);
        if(b){
            return RespBean.success("删除成功");
        }
//        return  siteService.updateSite(site);
        return RespBean.error("删除失败");

    }



//    ========================工人使用

    @ApiOperation("多条件分页查找工地")
    @PostMapping(value = {"/search/{currentPage}/{size}/{beginDateScope}","/search/{currentPage}/{size}"})
    public RespPageBean searchByPage(@RequestBody Site site, @PathVariable Integer currentPage,@PathVariable Integer size,@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate[] beginDateScope){
        System.out.println("多条件分页查询工人"+site);
        if(currentPage == null) currentPage = 1;
        if(size == null) size =5;
        return siteService.searchSiteByPage(site,currentPage,size,beginDateScope);
    }

//    根据工地id获取公司id
    @ApiOperation("根据工地id获取公司id")
    @GetMapping(value = "/getCompanyId/{sid}")
    public Long getCompanyIdBySid(@PathVariable Long sid){
        QueryWrapper<CompanySite> queryWrapper  = new QueryWrapper<>();
        queryWrapper.eq("sid",sid);
        CompanySite one = companySiteService.getOne(queryWrapper);
        return one.getCompanyId();
    }


//    =========================系统管理员使用
    @ApiOperation("获取所有工地信息")
    @GetMapping("/admin/getAllSite/{currentPage}/{size}")
    public RespPageBean getAllSite(@PathVariable Integer currentPage,@PathVariable Integer size){
        return siteService.getAllSite(currentPage,size);
    }

    //导出公司
    @ApiOperation("导出工地")
    @GetMapping(value = "/admin/export",produces = "application/octet-stream")
    public void exportCompany(HttpServletResponse response){
        List<Site> list = siteService.exportAllSite();
        ExportParams params = new ExportParams("工地表", "工地表");
        Workbook workbook = ExcelExportUtil.exportExcel(params, Site.class, list);
        ServletOutputStream out = null;
        try{
//            流形式
            response.setHeader("content-type","application/octet-stream");
//            防止中文乱码
//            response.setHeader("Content-Disposition", "attachment;filename*= UTF-8''"+ URLEncoder.encode("员工表.xls","UTF-8"));
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("工地表.xls","UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
            System.out.println(out);
        }
        catch (IOException e){
            e.printStackTrace();

        }
        finally {
            if(null!=out){
                try {
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
