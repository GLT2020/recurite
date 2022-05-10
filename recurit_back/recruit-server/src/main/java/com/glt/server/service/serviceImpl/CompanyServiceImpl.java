package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.CompanyMapper;
import com.glt.server.mapper.CompanyRoleMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 企业用户 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyRoleMapper companyRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Role> getRoles(Long id) {

        return companyMapper.getRoles(id);
    }

    //根据名字获取ID
    @Override
    public Company getIdByName(String name) {

        return companyMapper.getIdByName(name);
    }

    //根据id修改密码
    @Override
    public boolean updatepasswordById(Long id, String newpassword) {
        return companyMapper.updatepasswordById(id,newpassword);
    }



    //忘记密码
    @Override
    public RespBean notPasssword(Company company,HttpServletRequest request) {
        //验证码验证
        String captcha = (String) request.getSession().getAttribute("emailcode");
        System.out.println("captcha->>>>>>>>>>>>>>>>"+captcha);

        if (StringUtils.isEmpty(company.getCode()) || !captcha.equalsIgnoreCase(company.getCode())){
            return RespBean.error("验证码输入错误，请重新输入！");
        }

        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",company.getPhone()).eq("email",company.getEmail());
        Company company1 = companyMapper.selectOne(queryWrapper);
        if(null == company1){
            return RespBean.error("输入信息有误，请重新输入");
        }
        //更换密码
        company.setUpdateTime(LocalDate.now());
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        companyMapper.updatepasswordById(company1.getId(),company.getPassword());
        return RespBean.success("密码修改成功");
    }



    //获取公司所有信息（包含工地）
    @Override
    public Company getCompanyById(int id) {
        return companyMapper.getCompanyById(id);
    }



    //=====================================管理员使用
    //获取待验证公司信息
    @Override
    public RespPageBean verifyListPage(Integer currentpage, Integer size) {
        Page<Company> page = new Page<>(currentpage, size, true);

        IPage<Company> companyIPage = companyMapper.verifyListPage(page);
        RespPageBean respPageBean = new RespPageBean(companyIPage.getTotal(), companyIPage.getRecords());
        return respPageBean;
    }

    //验证公司信息，修改角色
    @Override
    public RespBean verifyCompany(Company company, Integer verify) {
        if(verify == 1){
            //通过验证
            //1.修改公司用户的状态
            //2.修改公司用户的角色
            company.setState(2);
            company.setUpdateTime(LocalDate.now());
            companyMapper.updateById(company);
            UpdateWrapper<CompanyRole> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("adminId",company.getId());
            int update = companyRoleMapper.update(new CompanyRole(company.getId(), 5L), updateWrapper);
            if(update != 0){
                return RespBean.success("验证通过");
            }

        }else{
            //不通过验证
            company.setState(0);
            int i = companyMapper.updateById(company);
            if(i!=0){
                return RespBean.success("已不通过验证");
            }

        }
        return RespBean.error("出现错误，请重试");
    }


    //获取所有公司信息
    @Override
    public RespPageBean getAllCompany(Integer currentPage, Integer size) {
        Page<Company> page = new Page<>(currentPage, size, true);

        IPage<Company> companyIPage = companyMapper.getAllCompanyByPage(page);
        RespPageBean respPageBean = new RespPageBean(companyIPage.getTotal(), companyIPage.getRecords());
        return respPageBean;
    }

    //获取所有公司信息用于导出
    @Override
    public List<Company> getAllCompanyToExport() {
        return companyMapper.getAllCompanyToExport();
    }

}
