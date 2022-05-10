package com.glt.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 工人-工种表
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("worker_work_type")
@ApiModel(value = "WorkerWorkType对象", description = "工人-工种表")
public class WorkerWorkType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("工人id")
    @TableField("workerId")
    private Long workerId;

    @ApiModelProperty("工种id")
    @TableField("tid")
    private Long tid;

    public WorkerWorkType(Long workerId,Long tid){
        this.workerId = workerId;
        this.tid = tid;
    }
}
