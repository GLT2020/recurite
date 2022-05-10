package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.Company;
import com.glt.server.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 企业用户 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

    List<Role> getRoles(Long id);

    //根据名字获取id
    Company getIdByName(String name);

    //根据id修改密码
    boolean updatepasswordById(Long id, String newpassword);

    //获取公司所有信息（包含工地）(没有写sql语句！！！！)
    Company getCompanyById(int id);

    //获取待验证公司信息
    IPage<Company> verifyListPage(Page<Company> page);

    //获取公司所有信息
    IPage<Company> getAllCompanyByPage(Page<Company> page);

    //获取所有公司信息用于导出
    List<Company> getAllCompanyToExport();
}
