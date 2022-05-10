package com.glt.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.glt.server.config.security.component.JwtTokenUtil;
import com.glt.server.mapper.CompanyMapper;
import com.glt.server.mapper.CompanySiteMapper;
import com.glt.server.mapper.SiteMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.CompanyRoleService;
import com.glt.server.service.CompanyService;
import com.glt.server.service.CompanySiteService;
import com.glt.server.service.SiteService;
import com.glt.server.service.serviceImpl.EmailSenderService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 企业用户 前端控制器
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyRoleService companyRoleService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private CompanySiteService companySiteService;

    @Autowired
    private CompanySiteMapper companySiteMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;



    //注册
    @ApiOperation("注册公司用户")
    @PostMapping("/register")
    public RespBean register(@RequestBody Company company, HttpServletRequest request){
        System.out.println("公司注册中的数据"+company.toString());

        //验证码验证
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println("captcha->>>>>>>>>>>>>>>>"+captcha);
        System.out.println("登录验证的captcha"+captcha+">>>session中的:"+request.getSession().getAttribute("captcha"));
        if (StringUtils.isEmpty(company.getCode()) || !captcha.equalsIgnoreCase(company.getCode())){
            return RespBean.error("验证码输入错误，请重新输入！");
        }

        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("name",company.getName()).or().eq("phone",company.getPhone());
        Company fcompany = companyService.getOne(wrapper);
//        Company fcompany = companyService.getIdByName(company.getName());
        if(fcompany != null){
            return RespBean.error("注册失败，该公司名或电话号已存在");
        }
        //自定义初始化数据
        company.setCreateTime(LocalDate.now());
        company.setUpdateTime(LocalDate.now());
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        System.out.println(company.getPassword());
        boolean save = companyService.save(company);
        if(save == false){
            return RespBean.error("注册失败,请重试");
        }
        //获取用户id
        Company ncompany = companyService.getIdByName(company.getName());
        Long id = ncompany.getId();

        //增加  角色与企业
        companyRoleService.save(new CompanyRole(id,4L));
        return RespBean.success("注册成功,请耐心等候验证");
    }

    //上传营业执照
    @ApiOperation("上传营业执照")
    @PostMapping("/uploadImg")
    public String uploadImg(@RequestParam("file") MultipartFile file){
//        System.out.println("上传营业执照时的用户名字："+principal.getName());

        System.out.println("上传的图片数据"+file);
        String imgString = "";
        HashMap<String, Object> resultMap = new HashMap<>();
        if(!file.isEmpty()){
            //图片路径


            String fileName = file.getOriginalFilename(); // 获取文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));// 获取文件的后缀名
//            String path = "D://RecuritProjects//myimg//";// 文件上传后存储的位置
            String path = "D:\\IDE\\recurit\\recruit-server\\src\\main\\resources\\static\\img";// 文件上传后存储的位置

            //为防止文件重名被覆盖，为每个文件都生成不同的名字
            UUID uuid = UUID.randomUUID();//生成一个唯一标识符
            String newFileName = uuid.toString().replaceAll("-", "") + suffixName;
            System.out.println("新文件名newFileName：" + newFileName);
            File dir = new File(path, newFileName);//创建文件流，对文件操作
            File filepath = new File(path);
            if (!filepath.exists()) {
                filepath.mkdirs();
            }
            try {
                file.transferTo(dir);//将文件 传送到之前创建的文件流（把图片写进去）
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (imgString.equals(""))
                imgString = imgString+"http://localhost:8081/myimg/"+newFileName;//这里的myimg路径是要自己配置的，下面再介绍
            else
                imgString = imgString + "," +"http://localhost:8081/myimg/" + newFileName;
        }
        return imgString;
    }


    @ApiOperation(value = "发送邮箱")
    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody Company company, HttpSession session){
        String mail = company.getEmail();
        String code = emailSenderService.sendEmail(mail);
        if(null != code && !"".equals(code)){
            session.setAttribute("emailcode",code);
            System.out.println("发送邮箱的验证码"+code);
//            return RespBean.success("邮箱发送成功！");
        }
//        return RespBean.error("邮箱发送失败，请重试");

    }


    //忘记密码
    @ApiOperation("忘记密码")
    @PostMapping("/notPassword")
    public RespBean notPassword(@RequestBody Company company, HttpServletRequest request){
        return companyService.notPasssword(company,request);
    }

    //根据id获取公司用户
    @ApiOperation("根据id获取公司用户")
    @GetMapping("/{id}")
    public Company info(@PathVariable int id){
        return companyService.getById(id);
    }

    //更新
    @ApiOperation("更新用户信息")
    @PutMapping("/")
    public RespBean update(@RequestBody Company company){
        company.setUpdateTime(LocalDate.now());
        company.setState(1);
        //更新公司用户角色
        UpdateWrapper<CompanyRole> workerRoleWrapper = new UpdateWrapper<CompanyRole>();
        workerRoleWrapper.eq("adminId",company.getId());
        CompanyRole companyRole = new CompanyRole(company.getId(), 4L);
        companyRoleService.update(companyRole,workerRoleWrapper);

        boolean b = companyService.updateById(company);
        if(b){
            //更新redis
            NameHelper nameHelper = new NameHelper();
            nameHelper.setPhone(company.getPhone());
            nameHelper.setState(1);
            System.out.println("更新公司用户的nameHelper的电话号"+nameHelper.getPhone());
            LoginUser userDetails = (LoginUser) userDetailsService.loadUserByUsername(JSON.toJSONString(nameHelper));
            redisTemplate.opsForValue().set("login1:"+userDetails.getId(),userDetails);
            return RespBean.success("更新成功");
        }
        else{
            return RespBean.error("更新失败，请联系管理员");
        }
    }

    //更新密码
    @ApiOperation("更新用户密码")
    @PutMapping("/password/{oldpassword}/{newpassword}")
    public RespBean updatepassword(@RequestBody Company company,@PathVariable String oldpassword,@PathVariable String newpassword){

        System.out.println("旧密码："+oldpassword+"新密码："+newpassword);
        System.out.println(";判断是否一致"+(newpassword.equals(oldpassword))+"判断旧密码是否一致:"+(passwordEncoder.matches(oldpassword,company.getPassword())));

        //如果输入的旧密码不等于原来的密码 或s新密码不等于原来的密码
        if(!passwordEncoder.matches(oldpassword,company.getPassword()) || newpassword.equals(oldpassword)){
            return RespBean.error("旧密码输入错误或新旧密码相同");
        }
        else{
            //修改密码
            boolean flag = companyService.updatepasswordById(company.getId(),passwordEncoder.encode(newpassword));
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

    //获取公司属下所有工地
    @ApiOperation("获取公司属下所有工地")
    @GetMapping("/sites/{id}")
    public List<Site> getAllsites(@PathVariable int id){
//        return companyService.getCompanyAndSites();
        return  siteService.getcompanySites(id);
    }

    //获取公司属下招聘中的工地
    @ApiOperation("获取公司属下招聘中的工地")
    @GetMapping("/recuritySites/{id}")
    public List<Site> getrecuritySites(@PathVariable int id){

        return  siteService.getcompanyrecuritySites(id);
    }

    //创建工地
    @ApiOperation("创建工地")
    @PostMapping("/createSite/{id}")
    //判断访问用户是否有company角色
    @PreAuthorize("hasRole('company')")
    public RespBean createSite(@RequestBody Site site,@PathVariable Long id){
        site.setCreateTime(LocalDate.now());
        site.setSiteState(1);
        Long coloum = siteMapper.insertGetId(site);
        //获取long类型的sid
        Long sid = site.getId();
//        将工地与公司关联
        int i = companySiteMapper.insert(new CompanySite( id, sid));
        if(i == 1){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败，请重试");


    }




//    ==================================================系统用户
    @ApiOperation("获取待验证的公司用户")
    @GetMapping("/admin/verify/{currentPage}/{size}")
    public RespPageBean getVerifyCompany(@PathVariable Integer currentPage,@PathVariable Integer size){

        return companyService.verifyListPage(currentPage,size);
    }

    @ApiOperation("验证公司用户")
    @PostMapping("/admin/verifyCompany/{verify}")
    public RespBean verifyCompany(@RequestBody Company company,@PathVariable Integer verify){

        return companyService.verifyCompany(company,verify);
    }

    @ApiOperation("获取所有公司信息")
    @GetMapping("/admin/getAllCompany/{currentPage}/{size}")
    public RespPageBean getAllCompany(@PathVariable Integer currentPage,@PathVariable Integer size){
        return companyService.getAllCompany(currentPage,size);
    }

    //更改公司状态
    @ApiOperation("更改公司状态")
    @PutMapping("/admin/updateState")
    public RespBean updateState(@RequestBody Company company){
        UpdateWrapper<Company> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",company.getId());
        boolean update = companyService.update(company,updateWrapper);
        if(update){
            return RespBean.success("更改成功");
        }
        return RespBean.error("更改失败，请重试");
    }

    //导出公司
    @ApiOperation("导出公司")
    @GetMapping(value = "/admin/export",produces = "application/octet-stream")
    public void exportCompany(HttpServletResponse response){
        List<Company> list = companyService.list();
        ExportParams params = new ExportParams("企业表", "企业表");
        Workbook workbook = ExcelExportUtil.exportExcel(params, Company.class, list);
        ServletOutputStream out = null;
        try{
//            流形式
            response.setHeader("content-type","application/octet-stream");
//            防止中文乱码
//            response.setHeader("Content-Disposition", "attachment;filename*= UTF-8''"+ URLEncoder.encode("员工表.xls","UTF-8"));
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("企业表.xls","UTF-8"));
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
