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
 * 管理员-角色表
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("admin_role")
@ApiModel(value = "AdminRole对象", description = "管理员-角色表")
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("管理员id")
    @TableField("adminId")
    private Long adminId;

    @ApiModelProperty("角色id")
    @TableField("rid")
    private Long rid;

    public AdminRole(Long adminId,Long rid){
        this.adminId =adminId;
        this.rid = rid;
    }


}
