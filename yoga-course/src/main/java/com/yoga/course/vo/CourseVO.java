package com.yoga.course.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 后台课程表视图对象
 *
 */
@ApiModel("后台课程表视图对象")
@Data
public class CourseVO {

    @ApiModelProperty(value = "时间段")
    private String rangeTime;

    @ApiModelProperty(value = "日期")
    private String Date;

    @ApiModelProperty(value = "周一某时间段所有课程")
    private List<CourseDetailVO> monday;

    @ApiModelProperty(value = "周二某时间段所有课程")
    private List<CourseDetailVO> tuesday;

    @ApiModelProperty(value = "周三某时间段所有课程")
    private List<CourseDetailVO> wednesday;

    @ApiModelProperty(value = "周四某时间段所有课程")
    private List<CourseDetailVO> thursday;

    @ApiModelProperty(value = "周五某时间段所有课程")
    private List<CourseDetailVO> friday;

    @ApiModelProperty(value = "周六某时间段所有课程")
    private List<CourseDetailVO> saturday;

    @ApiModelProperty(value = "周日某时间段所有课程")
    private List<CourseDetailVO> sunday;

}
