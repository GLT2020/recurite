package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.AdminMapper;
import com.glt.server.mapper.AdminRoleMapper;
import com.glt.server.mapper.RoleMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 管理员用户 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    获取用户角色
    @Override
    public List<Role> getRoles(Long id) {
        return adminMapper.getRoles(id);
    }

    //分页获取所有系统用户
    @Override
    public RespPageBean searchAllAdminByPage(Integer currentPage, Integer size) {
        Page<Admin> page = new Page<>(currentPage, size, true);
        page.setOptimizeCountSql(false);
        IPage<Admin> adminIPage;
        adminIPage = adminMapper.searchAllAdminByPage(page);
        System.out.println("按条件分页查询mapper层"+adminIPage.getPages());
        RespPageBean respPageBean = new RespPageBean(adminIPage.getTotal(), adminIPage.getRecords());
        return respPageBean;
    }

    //添加用户
    @Override
    public RespBean addAdmin(Admin admin, Long rid) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",admin.getPhone());
        Admin admin1 = adminMapper.selectOne(queryWrapper);
        if(null == admin1){
            //说明已经账号没被注册过
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setCreateTime(LocalDate.now());
            adminMapper.insert(admin);

            //添加角色关联
            int insert = adminRoleMapper.insert(new AdminRole(admin.getId(), rid));
            if(insert != 0){
                return RespBean.success("添加成功");
            }

        }
        else{
            return RespBean.error("该账号已被注册！");
        }
        return RespBean.error("出现错误，请重试");
    }

    //删除用户
    @Override
    public RespBean deleteAdmin(Admin admin) {
        QueryWrapper<AdminRole> rolequeryWrapper = new QueryWrapper<>();
        rolequeryWrapper.eq("adminId",admin.getId());
        adminRoleMapper.delete(rolequeryWrapper);
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("id",admin.getId());
        int delete = adminMapper.delete(adminQueryWrapper);
        if(delete!=0){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败，请重试");
    }
}
