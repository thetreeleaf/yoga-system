package com.yoga.course.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程细节查询对象
 *
 */
@Data
@ApiModel
public class CourseDetailQuery {

    @ApiModelProperty("周几")
    private String week;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty(value = "课程类型id")
    private Integer courseType;

    @ApiModelProperty(value = "会员id")
    private Integer memberId;

    @ApiModelProperty(value = "时间段")
    private String rangeTime;

}
