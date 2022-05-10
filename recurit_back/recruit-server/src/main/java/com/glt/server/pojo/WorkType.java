package com.glt.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 工种类
 * </p>
 *
 * @author glt
 * @since 2022-04-12
 */
@Getter
@Setter
@TableName("work_type")
@ApiModel(value = "WorkType对象", description = "工种类")
public class WorkType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("种类名")
    @TableField("name")
    @Excel(name = "种类名")
    private String name;

    @ApiModelProperty("是否启用")
    @TableField("state")
    private Integer state;

}
