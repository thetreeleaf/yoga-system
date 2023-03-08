package com.yoga.system.query;


import com.yoga.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户分页查询对象
 *
 */
@Data
@ApiModel
public class UserPageQuery extends BasePageQuery {

    @ApiModelProperty("关键字(用户名、昵称、手机号)")
    private String keywords;

    @ApiModelProperty("用户状态")
    private Integer status;

}

