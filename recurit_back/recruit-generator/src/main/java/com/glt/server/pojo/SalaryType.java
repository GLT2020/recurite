package com.glt.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 薪资种类
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("salary_type")
@ApiModel(value = "SalaryType对象", description = "薪资种类")
public class SalaryType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("种类名")
    @TableField("name")
    private String name;


}
