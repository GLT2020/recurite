package com.glt.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * 工人用户
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@ToString
@TableName("worker")
@ApiModel(value = "Worker对象", description = "工人用户")
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("姓名")
    @TableField("name")
    @Excel(name = "员工姓名",needMerge = true)
    private String name;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("电话号")
    @TableField("phone")
    @Excel(name = "电话号",width = 20,needMerge = true)
    private String phone;

    @ApiModelProperty("邮箱")
    @TableField("email")
    @Excel(name = "邮箱",width = 20,needMerge = true)
    private String email;

    @ApiModelProperty("常驻地址")
    @TableField("nowAddress")
    @Excel(name = "常驻地址",width = 30,needMerge = true)
    private String nowAddress;

    @ApiModelProperty("工作意向地址")
    @TableField("address")
    @Excel(name = "工作意向地址",width = 30,needMerge = true)
    private String address;

    @ApiModelProperty("学历")
    @TableField("education")
    @Excel(name = "学历",needMerge = true)
    private String education;

    @ApiModelProperty("性别")
    @TableField("sex")
    @Excel(name = "性别",needMerge = true)
    private String sex;

    @ApiModelProperty("出生年月")
    @TableField("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "出生日期",width = 20,format = "yyyy-MM-dd",needMerge = true)
    private LocalDate birthday;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "创建时间",width = 20,format = "yyyy-MM-dd",needMerge = true)
    private LocalDate createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "更新时间",width = 20,format = "yyyy-MM-dd",needMerge = true)
    private LocalDate updateTime;

    @ApiModelProperty("头像")
    @TableField("userface")
    private String userface;

    @ApiModelProperty("个人简述")
    @TableField("remark")
    @Excel(name = "个人简述",width = 20,needMerge = true)
    private String remark;

    @ApiModelProperty("状态（1 无职业 0已入职 0暂不找工）")
    @TableField("state")
    @Excel(name = "用户状态",replace = {"无职业_0","无职业_1","暂不找工_0"},needMerge = true)
    private Integer state;

    @ApiModelProperty("角色")
    @TableField(exist = false)
    private List<Role> roles;

    @ApiModelProperty("工种ID(用于注册)")
    @TableField(exist = false)
    private List<Integer> workTypes;

//    @ApiModelProperty("工种ID(用于搜索)")
//    @TableField(exist = false)
//    private Integer searchworkType;

    @ApiModelProperty("工种种类（招聘信息用和公司查找用户用）")
    @TableField(exist = false)
    @ExcelCollection(name = "工种")
    private List<WorkType> workTypeList;

    @ApiModelProperty("验证码")
    @TableField(exist = false)
    private String code;


    @ApiModelProperty("应聘时间")
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")

    private LocalDate appTime;

    @ApiModelProperty("年龄")
    @TableField(exist = false)
    @Excel(name = "年龄",needMerge = true)
    private Integer age;
}
