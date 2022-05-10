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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 招聘信息
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("recurit_message")
@ApiModel(value = "RecuritMessage对象", description = "招聘信息")
public class RecuritMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("招聘人数")
    @TableField("number")
    private Integer number;

    @ApiModelProperty("薪资")
    @TableField("salary")
    private String salary;

    @ApiModelProperty("薪资种类ID")
    @TableField("stype_id")
    private Integer stypeId;

    @ApiModelProperty("工种ID")
    @TableField("wtype_id")
    private Integer wtypeId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "出生日期",width = 20,format = "yyyy-MM-dd")
    private LocalDate createTime;

    @ApiModelProperty("需求简述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("招聘状态（0招聘中 1不招）")
    @TableField("state")
    private Integer state;

    @ApiModelProperty("薪资种类")
    @TableField(exist = false)
    private SalaryType salaryType;

    @ApiModelProperty("工种种类")
    @TableField(exist = false)
    private WorkType workType;

    @ApiModelProperty("工地")
    @TableField(exist = false)
    private Site site;
}
