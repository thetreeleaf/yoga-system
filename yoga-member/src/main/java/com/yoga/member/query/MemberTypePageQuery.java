package com.yoga.member.query;

import com.yoga.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员类型分页查询对象
 *
 */
@Data
@ApiModel
public class MemberTypePageQuery extends BasePageQuery {

    @ApiModelProperty("关键字(昵称、手机号)")
    private String keywords;

    @ApiModelProperty(value = "场馆id")
    private Integer placeId;

}
