package com.yoga.member.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class MembershipCardQuery {

    @ApiModelProperty("会员卡名称)")
    private String name;

    @ApiModelProperty(value = "场馆id")
    private Integer placeId;

}
