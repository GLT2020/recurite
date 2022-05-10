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
@TableName("adminmailbox")
@ApiModel(value = "Adminmailbox对象", description = "")
public class Adminmailbox implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("发件人id")
    @TableField("sender")
    private Long sender;

    @ApiModelProperty("发件人类型（1企业 2工人）")
    @TableField("sendType")
    private Integer sendType;

    @ApiModelProperty("创建时间")
    @TableField("createTime")
    private LocalDate createTime;

    @ApiModelProperty("发件人名")
    @TableField("sendName")
    private String sendName;

    @ApiModelProperty("主题")
    @TableField("topic")
    private String topic;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("类别")
    @TableField("type")
    private String type;

    @ApiModelProperty("状态（0未读 1已读）")
    @TableField("state")
    private Integer state;


}
