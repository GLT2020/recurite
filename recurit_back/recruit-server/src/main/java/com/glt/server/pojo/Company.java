package com.glt.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 企业用户
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@ToString
@TableName("company")
@ApiModel(value = "Company对象", description = "企业用户")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("公司名")
    @TableField("name")
    @Excel(name = "公司名",width = 20)
    private String name;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("电话号")
    @TableField("phone")
    @Excel(name = "电话号",width = 20)
    private String phone;

    @ApiModelProperty("公司地址")
    @TableField("address")
    @Excel(name = "公司地址",width = 30)
    private String address;

    @ApiModelProperty("公司执照")
    @TableField("license")

    private String license;

    @ApiModelProperty("法人")
    @TableField("person")
    @Excel(name = "法人")
    private String person;

    @ApiModelProperty("邮箱")
    @TableField("email")
    @Excel(name = "邮箱")
    private String email;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "创建时间",width = 20,format = "yyyy-MM-dd")
    private LocalDate createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "更新时间",width = 20,format = "yyyy-MM-dd")
    private LocalDate updateTime;

    @ApiModelProperty("头像")
    @TableField("userface")
    private String userface;

    @ApiModelProperty("公司简述")
    @TableField("remark")
    @Excel(name = "公司简述",width = 30)
    private String remark;

    @ApiModelProperty("启用状态（0 拉黑 1待验证 2启用）")
    @TableField("state")
    @Excel(name = "用户状态",replace = {"未通过_0","待验证_1","启用_2"},width = 20)
    private Integer state;


    @ApiModelProperty("角色")
    @TableField(exist = false)
    private List<Role> roles;

    @ApiModelProperty("验证码")
    @TableField(exist = false)
    private String code;

    @ApiModelProperty("工地")
    @TableField(exist = false)
    private List<Site> sites;

}
