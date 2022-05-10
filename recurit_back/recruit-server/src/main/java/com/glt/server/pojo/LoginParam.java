package com.glt.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AdminLogin对象",description = "")
public class LoginParam {
//    @ApiModelProperty(value = "用户名",required = true)
//    private String username;
    @ApiModelProperty(value = "电话号",required = true)
    private String phone;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
    @ApiModelProperty(value = "登录角色",required = true)
    private int state; //0系统用户 1企业 2工人
}
