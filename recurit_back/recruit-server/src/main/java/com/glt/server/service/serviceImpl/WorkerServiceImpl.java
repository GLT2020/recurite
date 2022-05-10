package com.glt.server.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glt.server.mapper.WorkerMapper;
import com.glt.server.mapper.WorkerWorkTypeMapper;
import com.glt.server.pojo.*;
import com.glt.server.service.WorkerService;
import com.glt.server.service.WorkerWorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 工人用户 服务实现类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerMapper, Worker> implements WorkerService {

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private WorkerWorkTypeService workerWorkTypeService;

    @Autowired
    private WorkerWorkTypeMapper workerWorkTypeMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Role> getRoles(Long id) {
        return workerMapper.getRoles(id);
    }


    //根据电话号获取id
    @Override
    public Worker getIdByphone(String phone) {
        return workerMapper.getIdByphone(phone);
    }

    //按条件分页查询工人
    @Override
    public RespPageBean searchWorkerByPage(Integer currentPage, Integer size, Worker worker,Integer searchWorkType) {
        Page<Worker> page = new Page<>(currentPage, size, true);
        page.setOptimizeCountSql(false);
        IPage<Worker> workerByPage ;
        if(searchWorkType != 0){
            workerByPage = workerMapper.searchWorkerWithWorkTypeByPage(page,worker,searchWorkType);
        }
        else{
             workerByPage = workerMapper.SearchWorkerByPage(page,worker);
        }
        System.out.println("按条件分页查询mapper层"+workerByPage.getPages());
        RespPageBean respPageBean = new RespPageBean(workerByPage.getTotal(), workerByPage.getRecords());
        return respPageBean;
    }

    //修改用户，并判断电话号是否有重复
    @Override
    public boolean updateWithPhone(Worker worker) {
        Worker oldworker = workerMapper.getWorkerWithWorkTypeListById(worker.getId().intValue());
        List<Integer> oldlist = new ArrayList<>();
        for (int i=0 ;i < worker.getWorkTypeList().size();i++){
            oldlist.add( oldworker.getWorkTypeList().get(i).getId().intValue());
        }
        oldworker.setWorkTypes(oldlist);

        QueryWrapper<Worker> wrapper = new QueryWrapper<>();
        System.out.println("修改工人用户的oldworker"+oldworker.toString());

        QueryWrapper<WorkerWorkType> typewrapper = new QueryWrapper<>();

        //判断电话号是否有修改,如果有修改,判断是否有重复,有重复则不能修改，否则进行修改
        if(!oldworker.getPhone().equals(worker.getPhone()) ){
            //电话号发生修改,根据新的电话号查找是否已有用户
            wrapper.eq("phone",worker.getPhone());
            Worker worker1 = workerMapper.selectOne(wrapper);
            if(worker1 != null){
                //说明电话号已被使用

                System.out.println("修改工人信息中电话号已被注册！！！！！！！！！");
                return false;

            }
            else{
                //说明电话号没被使用,直接进行修改
                workerMapper.updateById(worker);
            }
        }
        else{
            //电话号没被修改,直接进行修改
            workerMapper.updateById(worker);
        }
        //对工人-工种表进行修改
        //判断工种是否改变
        if(!oldlist.equals(worker.getWorkTypes())){
            //此时为改变,先删除，再添加
            typewrapper.eq("workerId",worker.getId());
            workerWorkTypeMapper.delete(typewrapper);
            List<WorkerWorkType> list = new ArrayList<>();
            for(int i =0;i < worker.getWorkTypes().size();i++){
                WorkerWorkType workerWorkType = new WorkerWorkType(worker.getId(), worker.getWorkTypes().get(i).longValue());
                list.add(workerWorkType);
            }
            workerWorkTypeService.saveBatch(list);

        }

        return true;
    }

    //修改密码
    @Override
    public boolean updatepasswordById(Long id, String password) {
        return workerMapper.updatepasswordById(id,password);
    }

    //忘记密码
    @Override
    public RespBean notPasssword(Worker worker, HttpServletRequest request) {
        //验证码验证
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println("captcha->>>>>>>>>>>>>>>>"+captcha);
        System.out.println("登录验证的captcha"+captcha+">>>session中的:"+request.getSession().getAttribute("captcha"));
        if (StringUtils.isEmpty(worker.getCode()) || !captcha.equalsIgnoreCase(worker.getCode())){
            return RespBean.error("验证码输入错误，请重新输入！");
        }

        QueryWrapper<Worker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",worker.getPhone()).eq("nowAddress",worker.getNowAddress()).eq("birthday",worker.getBirthday());
        Worker worker1 = workerMapper.selectOne(queryWrapper);
        if(null == worker1){
            return RespBean.error("信息有误，请重新输入");
        }
        //更换密码
        worker.setUpdateTime(LocalDate.now());
        worker.setPassword(passwordEncoder.encode(worker.getPassword()));
        workerMapper.updatepasswordById(worker1.getId(),worker.getPassword());
        return RespBean.success("密码修改成功");

    }

    //导出工人
    @Override
    public List<Worker> getAllWorkerToExport() {
        return workerMapper.getAllWorkerToExport();
    }


    //系统用户获取所有工人
    @Override
    public RespPageBean searchAllWorkerByPage(Integer currentPage, Integer size) {
        Page<Worker> page = new Page<>(currentPage, size, true);
        page.setOptimizeCountSql(false);
        IPage<Worker> workerByPage;
        workerByPage = workerMapper.searchAllWorkerByPage(page);
        System.out.println("按条件分页查询mapper层"+workerByPage.getPages());
        RespPageBean respPageBean = new RespPageBean(workerByPage.getTotal(), workerByPage.getRecords());
        return respPageBean;
    }


}
