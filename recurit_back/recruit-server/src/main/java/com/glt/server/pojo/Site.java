package com.glt.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 工地
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@Data
@TableName("site")
@ApiModel(value = "Site对象", description = "工地")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("工地名")
    @TableField("name")
    @Excel(name = "工地名",width = 20)
    private String name;

    @ApiModelProperty("工地负责人")
    @TableField("user_name")
    @Excel(name = "工地负责人",width = 20)
    private String userName;

    @ApiModelProperty("电话号")
    @TableField("phone")
    @Excel(name = "电话号",width = 20)
    private String phone;

    @ApiModelProperty("工地地址")
    @TableField("address")
    @Excel(name = "工地地址",width = 20)
    private String address;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "创建时间",width = 20,format = "yyyy-MM-dd")
    private LocalDate createTime;

    @ApiModelProperty("开工时间")
    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "开工时间",width = 20,format = "yyyy-MM-dd")
    private LocalDate startTime;

    @ApiModelProperty("完工时间")
    @TableField("complete_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "完工时间",width = 20,format = "yyyy-MM-dd")
    private LocalDate completeTime;

    @ApiModelProperty("预计工期")
    @TableField("expect_time")
    @Excel(name = "预计工期",width = 20)
    private String expectTime;

    @ApiModelProperty("头像")
    @TableField("userface")
    private String userface;

    @ApiModelProperty("工地简述")
    @TableField("remark")
    @Excel(name = "工地简述",width = 20)
    private String remark;

    @ApiModelProperty("招聘状态（1招聘中 0不招）")
    @TableField("recurit_state")
    @Excel(name = "招聘状态",replace = {"不招聘_0","招聘中_1"},width = 20)
    private Integer recuritState;

    @ApiModelProperty("工地状态（1施工中 0完工）")
    @TableField("site_state")
    @Excel(name = "工地状态",replace = {"已完工_0","施工中_1"},width = 20)
    private Integer siteState;




    @ApiModelProperty("拥有的招聘信息")
    @TableField(exist = false)
    private List<RecuritMessage> recuritMessageList;

    @ApiModelProperty("工地删除(0删除 1未删除）")
    @TableField("dflag")
    @Excel(name = "是否删除",replace = {"已删除_0","未删除_1"},width = 20)
    private Integer dflag;

    @ApiModelProperty("所属公司")
    @TableField(exist = false)
    @ExcelEntity(name = "所属公司",id = "company",show = true)
    private Company company;
}
