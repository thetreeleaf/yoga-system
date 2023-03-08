package com.yoga.course.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;


/**
 * 课程分页视图对象
 *
 */
@ApiModel("课程分页视图对象")
@Data
public class CoursePageVO {

    @ApiModelProperty(value = "课程id")
    private Integer id;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程简介")
    private String courseDesc;

    @ApiModelProperty(value = "课程引入时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stroeTime;

    @ApiModelProperty(value = "1: 团体课，2： 私教课")
    private Integer courseType;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "所属场馆id")
    private Integer placeId;

    @ApiModelProperty(value = "场馆名称")
    private String placeName;

}
