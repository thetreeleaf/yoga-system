package com.yoga.course.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
@Data
@TableName("course_range")
@ApiModel(value="CourseRange对象", description="")
public class CourseRange implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程安排id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "1：指定日期； 2：周循环")
    private Integer courseRangeType;

    @ApiModelProperty(value = "课程安排内容")
    private String courseRangeContent;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "教练id")
    private Integer coachId;

    @ApiModelProperty(value = "教室id")
    private Integer roomId;

    @ApiModelProperty(value = "时间段id")
    private Integer timeId;

}
