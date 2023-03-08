package com.yoga.system.query;


import com.yoga.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 教练分页查询对象
 *
 */
@Data
@ApiModel
public class CoachPageQuery extends BasePageQuery {

    @ApiModelProperty("关键字(教练名、场馆名、手机号)")
    private String keywords;

    @ApiModelProperty("场馆id")
    private Integer placeId;
}
