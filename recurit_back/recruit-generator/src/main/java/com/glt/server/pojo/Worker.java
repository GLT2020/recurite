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
 * 工人用户
 * </p>
 *
 * @author glt
 * @since 2022-04-14
 */
@Getter
@Setter
@TableName("worker")
@ApiModel(value = "Worker对象", description = "工人用户")
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("电话号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("常驻地址")
    @TableField("nowAddress")
    private String nowAddress;

    @ApiModelProperty("工作意向地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("学历")
    @TableField("education")
    private String education;

    @ApiModelProperty("性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("出生年月")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDate createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDate updateTime;

    @ApiModelProperty("头像")
    @TableField("userface")
    private String userface;

    @ApiModelProperty("个人简述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("状态（1 无职业 0已入职 0暂不找工）")
    @TableField("state")
    private Integer state;


}
