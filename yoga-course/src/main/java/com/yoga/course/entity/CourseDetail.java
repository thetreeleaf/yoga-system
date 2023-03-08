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
@TableName("course_detail")
@ApiModel(value="CourseDetail对象", description="")
public class CourseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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

}
