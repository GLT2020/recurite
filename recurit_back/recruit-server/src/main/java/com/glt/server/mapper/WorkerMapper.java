package com.glt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glt.server.pojo.Role;
import com.glt.server.pojo.Worker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工人用户 Mapper 接口
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Mapper
public interface WorkerMapper extends BaseMapper<Worker> {

    List<Role> getRoles(Long id);

    //根据电话号获取id
    Worker getIdByphone(String phone);

    //按条件(有工种条件)分页查询工人
    IPage<Worker> searchWorkerWithWorkTypeByPage(Page<Worker> page,@Param("worker") Worker worker,@Param("searchWorkType") Integer searchWorkType);

    //按条件(不含工种条件)分页查询工人
    IPage<Worker> SearchWorkerByPage(Page<Worker> page,@Param("worker") Worker worker);

    //根据id获取工人信息（包含工种列表）
    Worker getWorkerWithWorkTypeListById(Integer id);

    //修改密码
    boolean updatepasswordById(Long id, String password);


    //系统用户获取所有工人用户
    IPage<Worker> searchAllWorkerByPage(Page<Worker> page);

    //导出工人
    List<Worker> getAllWorkerToExport();
}
