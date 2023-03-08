package com.yoga.course.query;


import com.yoga.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程分页查询对象
 *
 */
@Data
@ApiModel
public class CoursePageQuery extends BasePageQuery {

    @ApiModelProperty("课程名")
    private String courseName;

    @ApiModelProperty("课程类型")
    private Integer courseType;

    @ApiModelProperty(" 场馆id")
    private String placeId;

}
