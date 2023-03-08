package com.yoga.member.query;

import com.yoga.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员分页查询对象
 *
 */
@Data
@ApiModel
public class MemberPageQuery extends BasePageQuery {

    @ApiModelProperty("昵称、手机号)")
    private String nickName;

    @ApiModelProperty("手机号)")
    private String mobile;

    @ApiModelProperty("会员类型")
    private Integer membershipCardId;

    @ApiModelProperty(value = "场馆id")
    private Integer placeId;
}
