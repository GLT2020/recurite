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
 * 企业用户
 * </p>
 *
 * @author glt
 * @since 2022-05-01
 */
@Getter
@Setter
@TableName("company")
@ApiModel(value = "Company对象", description = "企业用户")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("公司名")
    @TableField("name")
    private String name;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("电话号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("公司地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("公司执照")
    @TableField("license")
    private String license;

    @ApiModelProperty("法人")
    @TableField("person")
    private String person;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDate createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDate updateTime;

    @ApiModelProperty("头像")
    @TableField("userface")
    private String userface;

    @ApiModelProperty("公司简述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("启用状态（0 未通过 1待验证 2启用）")
    @TableField("state")
    private Integer state;


}
