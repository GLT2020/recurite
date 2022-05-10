package com.glt.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.glt.server.mapper.WorkerMapper;
import com.glt.server.mapper.WorkerRoleMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 工人用户 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private WorkerRoleService workerRoleService;

    @Autowired
    private WorkerWorkTypeService workerWorkTypeService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ApiOperation("工人注册")
    @PostMapping(value = "/register",produces = "application/json;charset=UTF-8")
    public RespBean register(@RequestBody Worker worker, HttpServletRequest request){
        //验证码验证
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println("captcha->>>>>>>>>>>>>>>>"+captcha);
        System.out.println("登录验证的captcha"+captcha+">>>session中的:"+request.getSession().getAttribute("captcha"));
        if (StringUtils.isEmpty(worker.getCode()) || !captcha.equalsIgnoreCase(worker.getCode())){
            return RespBean.error("验证码输入错误，请重新输入！");
        }

        Worker fworker = workerService.getIdByphone(worker.getPhone());
        if(fworker != null){
            return RespBean.success("注册失败，该电话号已存在");
        }
        //自定义初始化数据
        worker.setCreateTime(LocalDate.now());
        worker.setPassword(passwordEncoder.encode(worker.getPassword()));
        worker.setState(1);
        System.out.println(worker.getPassword());
        boolean save = workerService.save(worker);
        //添加工人与工种

        if(save == false){
            return RespBean.success("注册失败,请重试");
        }
        //获取用户id
        Worker nworker = workerService.getIdByphone(worker.getPhone());
        Long id = nworker.getId();
        System.out.println("添加工人-》》》工人工种id"+worker.getWorkTypes());

        //添加工人与工种关系
        if(worker.getWorkTypes() != null){
            workerWorkTypeService.add(id,worker.getWorkTypes());
        }


        //增加  角色与工人
        workerRoleService.save(new WorkerRole(id,3L));
        return RespBean.success("注册成功");
    }

    @ApiOperation("根据id获取工人信息")
    @GetMapping(value = "/{id}")
    public Worker getWorker(@PathVariable Integer id){

        Worker worker =workerMapper.getWorkerWithWorkTypeListById(id);
        List<Integer> list = new ArrayList<>();
        for (int i=0 ;i < worker.getWorkTypeList().size();i++){
            list.add( worker.getWorkTypeList().get(i).getId().intValue());
        }
        worker.setWorkTypes(list);
        return worker;
    }


    @ApiOperation("多条件分页查找工人")
    @PostMapping(value = {"/search/{currentPage}/{size}/{searchWorkType}","/search/{currentPage}/{size}"})
    public RespPageBean searchByPage(@RequestBody Worker worker, @PathVariable Integer currentPage, @PathVariable Integer size,@PathVariable(required = false) Integer searchWorkType){
        //设置两个默认值
        System.out.println("多条件分页查询工人"+worker);
        if(currentPage == null) currentPage = 1;
        if(size == null) size =5;
        if(searchWorkType == null) searchWorkType = 0;
        return workerService.searchWorkerByPage(currentPage,size,worker,searchWorkType);
    }

    @ApiOperation("根据ID修改工人信息")
    @PutMapping(value = "/update")
    public RespBean updateWorker(@RequestBody Worker worker){
        System.out.println("修改工人用户信息的worker"+worker.toString());
       boolean flag =  workerService.updateWithPhone(worker);
       if(flag == false){

           return RespBean.error("电话号已被注册,请重试");
       }
        return RespBean.success("修改成功");
    }

    //更新
    @ApiOperation("更新用户密码")
    @PutMapping("/password/{oldpassword}/{newpassword}")
    public RespBean updatepassword(@RequestBody Worker worker,@PathVariable String oldpassword,@PathVariable String newpassword){

        System.out.println("旧密码："+oldpassword+"新密码："+newpassword);
        System.out.println(";判断是否一致"+(newpassword.equals(oldpassword))+"判断旧密码是否一致:"+(passwordEncoder.matches(oldpassword,worker.getPassword())));

        //如果输入的旧密码不等于原来的密码 或s新密码不等于原来的密码
        if(!passwordEncoder.matches(oldpassword,worker.getPassword()) || newpassword.equals(oldpassword)){
            return RespBean.error("旧密码输入错误或新旧密码相同");
        }
        else{
            //修改密码
            boolean flag = workerService.updatepasswordById(worker.getId(),passwordEncoder.encode(newpassword));
//            boolean flag = companyService.updatepasswordById(company.getId(),newpassword);
            System.out.println("flag->>>>"+flag);
            if(flag == false){
                return RespBean.error("修改错误，请重试");
            }
            else{
                return RespBean.success("修改成功");
            }
        }
    }

    //忘记密码
    @ApiOperation("忘记密码")
    @PostMapping("/notPassword")
    public RespBean notPassword(@RequestBody Worker worker, HttpServletRequest request){
        return workerService.notPasssword(worker,request);
    }

    //=====================企业和管理员使用
    @ApiOperation("分页查找所有工人")
    @PostMapping(value = {"/admin/search/{currentPage}/{size}"})
    public RespPageBean searchAllWorkerByPage(@PathVariable Integer currentPage, @PathVariable Integer size){
        //设置两个默认值

        if(currentPage == null) currentPage = 1;
        if(size == null) size =10;

        return workerService.searchAllWorkerByPage(currentPage,size);
    }


    //====================系统管理员使用
    //导出工人
    @ApiOperation("导出工人")
    @GetMapping(value = "/admin/export",produces = "application/octet-stream")
    public void exportCompany(HttpServletResponse response){
        List<Worker> list = workerService.getAllWorkerToExport();
//        ExportParams params = new ExportParams("工人表", "工人表", ExcelType.XSSF);
        list.forEach(item->{
            System.out.println("导出员工的数据："+item.toString());
        });
        ExportParams params = new ExportParams("工人表", "工人表");
        Workbook workbook = ExcelExportUtil.exportExcel(params, Worker.class, list);
        ServletOutputStream out = null;
        try{
//            流形式
            response.setHeader("content-type","application/octet-stream");
//            防止中文乱码
//            response.setHeader("Content-Disposition", "attachment;filename*= UTF-8''"+ URLEncoder.encode("员工表.xls","UTF-8"));
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("工人表.xls","UTF-8"));
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
