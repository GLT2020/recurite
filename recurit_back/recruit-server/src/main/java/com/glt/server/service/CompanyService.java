package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.Company;
import com.glt.server.pojo.RespBean;
import com.glt.server.pojo.RespPageBean;
import com.glt.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 企业用户 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface CompanyService extends IService<Company> {

    List<Role> getRoles(Long id);

    //根据名字获取id
    Company getIdByName(String name);

    //根据id修改密码
    boolean updatepasswordById(Long id, String newpassword);

    //获取公司所有信息（包含工地）
    Company getCompanyById(int id);

    //获取待验证公司信息
    RespPageBean verifyListPage(Integer page, Integer size);

    //验证公司信息，修改角色
    RespBean verifyCompany(Company company, Integer verify);

    //获取所有公司信息
    RespPageBean getAllCompany(Integer currentPage, Integer size);

    RespBean notPasssword(Company company, HttpServletRequest request);

    //获取所有公司信息用于导出
    List<Company> getAllCompanyToExport();
}
