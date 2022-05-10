package com.glt.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 工人-角色表
 * </p>
 *
 * @author glt
 * @since 2022-04-11
 */
@Getter
@Setter
@TableName("worker_role")
@ApiModel(value = "WorkerRole对象", description = "工人-角色表")
public class WorkerRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("工人id")
    @TableField("adminId")
    private Long adminId;

    @ApiModelProperty("角色id")
    @TableField("rid")
    private Long rid;


}
