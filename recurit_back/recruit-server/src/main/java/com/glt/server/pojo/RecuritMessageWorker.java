package com.glt.server.pojo;

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

/**
 * <p>
 * 招聘信息-工人表
 * </p>
 *
 * @author glt
 * @since 2022-04-14
 */
@Getter
@Setter
@TableName("recurit_message_worker")
@ApiModel(value = "RecuritMessageWorker对象", description = "招聘信息-工人表")
public class RecuritMessageWorker implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("工人id")
    @TableField("workerId")
    private Long workerId;

    @ApiModelProperty("招聘信息id")
    @TableField("rid")
    private Long rid;

    @ApiModelProperty("应聘时间")
    @TableField("createTime")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate createTime;

    public RecuritMessageWorker(Long workerId,Long rid){
        this.workerId = workerId;
        this.rid =rid;
    }
}
