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
 * 工地-招聘信息表
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("site_recurit_message")
@ApiModel(value = "SiteRecuritMessage对象", description = "工地-招聘信息表")
public class SiteRecuritMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("工地id")
    @TableField("siteId")
    private Long siteId;

    @ApiModelProperty("招聘信息id")
    @TableField("rid")
    private Long rid;

    public SiteRecuritMessage(Long siteId,Long rid){
        this.rid = rid;
        this.siteId = siteId;
    }
}
