package com.yoga.course.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程安排数据传输对象
 *
 */
@Data
@ApiModel
public class CourseRangeDto {


    @ApiModelProperty(value = "课程安排id")
    private Integer id;

    @ApiModelProperty(value = "1：指定日期； 2：周循环")
    private Integer courseRangeType;

    @ApiModelProperty(value = "课程安排内容")
    private String courseRangeContent;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "教练id")
    private Integer coachId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "时间段")
    private String rangeTime;

    @ApiModelProperty(value = "教室id")
    private Integer roomId;

    @ApiModelProperty(value = "时间段id")
    private Integer timeId;

}
