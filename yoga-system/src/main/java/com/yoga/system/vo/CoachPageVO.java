package com.yoga.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 教练分页视图对象
 *
 */
@ApiModel("教练分页视图对象")
@Data
public class CoachPageVO {


    @ApiModelProperty(value = "教练id")
    private Integer coachId;

    @ApiModelProperty(value = "教练名称")
    private String coachName;

    @ApiModelProperty(value = "教练工资")
    private BigDecimal coachSalary;

    @ApiModelProperty(value = "教练简介")
    private String coachIntro;

    @ApiModelProperty(value = "所属场馆id")
    private Integer placeId;

    @ApiModelProperty(value = "逻辑删除标识：0-未删除；1-已删除")
    private Integer deleted;

    @ApiModelProperty("场馆名称")
    private String placeName;

    @ApiModelProperty("入职时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @ApiModelProperty("离职时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    @ApiModelProperty(value = "联系号码")
    private String phone;

    @ApiModelProperty("性别(1:男;2:女)")
    private Integer gender;
}
