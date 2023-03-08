package com.yoga.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("course_state")
@ApiModel(value="CourseState对象", description="")
public class CourseState implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程状态id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "课程细节id")
    private Integer detailId;

    @ApiModelProperty(value = "1：未签到；2：已签到；3；已预约；4：取消预约")
    private Integer state;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "具体时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationTime;

    @ApiModelProperty(value = "教练id")
    private Integer coachId;

}
