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
 * 招聘信息
 * </p>
 *
 * @author glt
 * @since 2022-04-12
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
    private LocalDate createTime;

    @ApiModelProperty("需求简述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("招聘状态（0招聘中 1不招）")
    @TableField("state")
    private Integer state;


}
