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
 * 权限-角色表
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("menu_role")
@ApiModel(value = "MenuRole对象", description = "权限-角色表")
public class MenuRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("菜单id")
    @TableField("mid")
    private Long mid;

    @ApiModelProperty("角色id")
    @TableField("rid")
    private Long rid;


}
