package com.yoga.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户表单视图对象
 *
 */
@ApiModel("用户表单视图对象")
@Data
public class UserFormVO {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别(1:男;2:女)")
    private Integer gender;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户状态(1:启用;0:禁用)")
    private Integer status;

    @ApiModelProperty("用户角色ID的集合")
    private List<Long> roleIds;

    @ApiModelProperty(value = "场馆id")
    private Long placeId;

}
