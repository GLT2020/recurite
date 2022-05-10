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
 * 工地
 * </p>
 *
 * @author glt
 * @since 2022-04-20
 */
@Getter
@Setter
@TableName("site")
@ApiModel(value = "Site对象", description = "工地")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("工地名")
    @TableField("name")
    private String name;

    @ApiModelProperty("工地负责人")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("电话号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("工地地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDate createTime;

    @ApiModelProperty("开工时间")
    @TableField("start_time")
    private LocalDate startTime;

    @ApiModelProperty("完工时间")
    @TableField("complete_time")
    private LocalDate completeTime;

    @ApiModelProperty("预计工期")
    @TableField("expect_time")
    private String expectTime;

    @ApiModelProperty("头像")
    @TableField("userface")
    private String userface;

    @ApiModelProperty("工地简述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("招聘状态（1招聘中 0不招）")
    @TableField("recurit_state")
    private Integer recuritState;

    @ApiModelProperty("工地状态（1施工中 0完工）")
    @TableField("site_state")
    private Integer siteState;

    @ApiModelProperty("工地删除(0删除 1未删除）")
    @TableField("dflag")
    private Integer dflag;


}
