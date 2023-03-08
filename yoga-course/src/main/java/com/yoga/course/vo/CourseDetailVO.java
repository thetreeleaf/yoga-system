package com.yoga.course.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 课程细节视图对象
 *
 */
@ApiModel("课程细节视图对象")
@Data
public class CourseDetailVO {

    @ApiModelProperty(value = "课程细节id")
    private Integer id;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "教练id")
    private Integer coachId;

    @ApiModelProperty(value = "时间段")
    private String rangeTime;

    @ApiModelProperty(value = "周几")
    private String week;

    @ApiModelProperty(value = "教室id")
    private Integer roomId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "教练名称")
    private String coachName;

    @ApiModelProperty(value = "课程类型id")
    private Integer courseType;

    @ApiModelProperty(value = "教室类型id")
    private Integer roomType;

    @ApiModelProperty(value = "课程简介")
    private String courseDesc;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "按钮内容")
    private String buttonText;

    @ApiModelProperty(value = "按钮状态")
    private String disable;

}

