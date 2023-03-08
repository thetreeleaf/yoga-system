package com.yoga.system.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.yoga.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-09
 */
@Data
@TableName("coach")
@ApiModel(value="Coach对象", description="")
public class Coach extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教练id")
    @TableId(value = "coach_id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "联系号码")
    private String phone;

    @ApiModelProperty(value = "性别：1-男 2-女")
    private Integer gender;

}
