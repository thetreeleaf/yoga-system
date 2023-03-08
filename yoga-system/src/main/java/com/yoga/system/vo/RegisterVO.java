package com.yoga.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("注册视图对象")
@Data
public class RegisterVO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "场馆名称")
    private String placeName;

    @ApiModelProperty(value = "场馆地址")
    private String address;

    @ApiModelProperty(value = "场馆号码")
    private String phone;

    @ApiModelProperty(value = "用户邮箱")
    private String email;
}
