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
 * 企业-角色表
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("company_role")
@ApiModel(value = "CompanyRole对象", description = "企业-角色表")
public class CompanyRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("企业id")
    @TableField("adminId")
    private Long adminId;

    @ApiModelProperty("角色id")
    @TableField("rid")
    private Long rid;

    public CompanyRole(Long adminId,Long rid){
        this.adminId = adminId;
        this.rid = rid;
    }
}
