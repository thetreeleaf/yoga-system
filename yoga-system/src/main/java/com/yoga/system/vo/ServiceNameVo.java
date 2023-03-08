package com.yoga.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("服务名对象")
@Data
public class ServiceNameVo {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("服务名称")
    private String name;
}
