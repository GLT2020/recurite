package com.glt.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("NameHelper")
@ApiModel(value = "NameHelper对象", description = "协助多种用户登录")
public class NameHelper {
    private String username;
    private String phone;
    private int state;
}
