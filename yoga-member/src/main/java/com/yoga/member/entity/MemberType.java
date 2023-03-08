package com.yoga.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-27
 */
@Data
@TableName("member_type")
@ApiModel(value="MemberType对象", description="")
public class MemberType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员类型id")
    @TableId(value = "member_type_id", type = IdType.AUTO)
    private Integer memberTypeId;

    @ApiModelProperty(value = "会员类型名称")
    private String memberTypeName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "会员id")
    private Integer memberId;

    @ApiModelProperty(value = "会员卡id")
    private Integer memberCardId;

    @ApiModelProperty(value = "场馆id")
    private Integer placeId;

}
