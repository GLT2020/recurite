package com.glt.server.pojo;

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
 * 企业-工地表
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("company_site")
@ApiModel(value = "CompanySite对象", description = "企业-工地表")
public class CompanySite implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("企业id")
    @TableField("companyId")
    private Long companyId;

    @ApiModelProperty("工地id")
    @TableField("sid")
    private Long sid;

    public CompanySite(Long companyId,Long sid){
        this.companyId = companyId;
        this.sid = sid;
    }

}
