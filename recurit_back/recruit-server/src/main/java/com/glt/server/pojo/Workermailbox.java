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
 * 工人邮箱
 * </p>
 *
 * @author glt
 * @since 2022-04-03
 */
@Getter
@Setter
@TableName("workermailbox")
@ApiModel(value = "Workermailbox对象", description = "工人邮箱")
public class Workermailbox implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate createTime;

    @ApiModelProperty("主题")
    @TableField("topic")
    private String topic;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("工人id")
    @TableField("workerId")
    private Long workerId;

    @ApiModelProperty("工地id")
    @TableField("siteId")
    private Long siteId;

    @ApiModelProperty("工种id")
    @TableField("worktypeId")
    private Long worktypeId;

    @ApiModelProperty("类别（0普通 1推荐）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("状态（0未读 1已读）")
    @TableField("state")
    private Integer state;

    @ApiModelProperty("公司删除标注（1为未删除 0 为删除了）")
    @TableField("comdflag")
    private Integer comdflag;

    @ApiModelProperty("工人删除标注（1为未删除 0 为删除了）")
    @TableField("workdflag")
    private Integer workdflag;

    @ApiModelProperty("工人")
    @TableField(exist = false)
    private Worker worker;

    @ApiModelProperty("工地")
    @TableField(exist = false)
    private Site site;

    @ApiModelProperty("公司")
    @TableField(exist = false)
    private Company company;

    @ApiModelProperty("工种")
    @TableField(exist = false)
    private WorkType workType;
}
