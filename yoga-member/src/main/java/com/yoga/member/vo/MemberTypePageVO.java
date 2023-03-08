package com.yoga.member.vo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会员类型分页视图对象
 *
 */
@ApiModel("会员类型分页视图对象")
@Data
public class MemberTypePageVO {


    @TableId(value = "id")
    private Long memberId;

    @ApiModelProperty(value = "性别： 0：女，1：男")
    private Integer gender;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "会员类型名称")
    private String memberTypeName;

    @ApiModelProperty("会员开通时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("会员过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty(value = "会员卡id")
    private Integer memberCardId;

    @ApiModelProperty(value = "场馆id")
    private Integer placeId;
}
