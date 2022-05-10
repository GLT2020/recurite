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
 * 
 * </p>
 *
 * @author glt
 * @since 2022-04-23
 */
@Getter
@Setter
@TableName("sysmessage")
@ApiModel(value = "Sysmessage对象", description = "")
public class Sysmessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("主题")
    @TableField("topic")
    private String topic;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("创建时间")
    @TableField("createTime")
    private LocalDate createTime;

    @TableField("state")
    private Integer state;

    @TableField("type")
    private Integer type;


}
