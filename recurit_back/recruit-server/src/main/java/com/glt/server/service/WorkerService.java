package com.glt.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glt.server.pojo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 工人用户 服务类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
public interface WorkerService extends IService<Worker> {

    List<Role> getRoles(Long id);

    //根据电话号获取id
    Worker getIdByphone(String phone);

    //查询分页查询工人
    RespPageBean searchWorkerByPage(Integer currentPage, Integer size, Worker worker,Integer searchWorkType);

    //修改用户，并判断电话号是否有重复
    boolean updateWithPhone(Worker worker);

    //修改密码
    boolean updatepasswordById(Long id, String encode);

    //系统用户获取所有工人
    RespPageBean searchAllWorkerByPage(Integer currentPage, Integer size);

    //忘记密码
    RespBean notPasssword(Worker worker, HttpServletRequest request);

    //导出工人
    List<Worker> getAllWorkerToExport();
}
