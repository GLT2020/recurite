package com.glt.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 管理员用户
 * </p>
 *
 * @author glt
 * @since 2022-04-12
 */
@Getter
@Setter
@TableName("admin")
@ApiModel(value = "Admin对象", description = "管理员用户")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    @TableField("name")
    private String name;

    @TableField("phone")
    private String phone;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDate createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDate updateTime;

    @ApiModelProperty("头像")
    @TableField("userface")
    private String userface;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
