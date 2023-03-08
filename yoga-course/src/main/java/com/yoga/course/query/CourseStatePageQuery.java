package com.yoga.course.query;


import com.yoga.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程状态分页查询对象
 *
 */
@Data
@ApiModel
public class CourseStatePageQuery extends BasePageQuery {


    @ApiModelProperty(value = "关键词（教练名，课程名，昵称）")
    private String keywords;

    @ApiModelProperty(" 场馆id")
    private String placeId;

    @ApiModelProperty(value = "1：未签到；2：已签到；3；已预约；4：取消预约")
    private Integer state;

    @ApiModelProperty("用户id")
    private String userId;

}
