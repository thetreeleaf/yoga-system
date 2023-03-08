package com.yoga.course.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 课程状态分页视图对象
 *
 */
@ApiModel("课程状态分页视图对象")
@Data
public class CourseStatePageVO {

    @ApiModelProperty(value = "课程状态id")
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
    private Date operationTime;

    @ApiModelProperty(value = "教练id")
    private Integer coachId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "教练名")
    private String coachName;

    @ApiModelProperty(value = "时间段")
    private String rangeTime;

    @ApiModelProperty(value = "周几")
    private String week;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;
}
