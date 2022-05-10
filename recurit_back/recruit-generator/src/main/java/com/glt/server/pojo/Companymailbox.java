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
 * 企业邮箱
 * </p>
 *
 * @author glt
 * @since 2022-04-20
 */
@Getter
@Setter
@TableName("companymailbox")
@ApiModel(value = "Companymailbox对象", description = "企业邮箱")
public class Companymailbox implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("发件人id")
    @TableField("sender")
    private Integer sender;

    @ApiModelProperty("接收人id")
    @TableField("receiver")
    private Integer receiver;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDate createTime;

    @ApiModelProperty("主题")
    @TableField("topic")
    private String topic;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("工地id")
    @TableField("siteId")
    private Long siteId;

    @ApiModelProperty("工种id")
    @TableField("worktypeId")
    private Long worktypeId;

    @ApiModelProperty("类别（0普通 1邀请 2回信）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("状态（0未读 1已读 ）")
    @TableField("state")
    private Integer state;

    @ApiModelProperty("公司删除标注（1为未删除 0 为删除）")
    @TableField("comdflag")
    private Integer comdflag;

    @ApiModelProperty("工人删除标注（1为未删除 0为删除）")
    @TableField("workdflag")
    private Integer workdflag;


}
