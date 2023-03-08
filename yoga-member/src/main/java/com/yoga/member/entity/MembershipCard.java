package com.yoga.member.entity;

import java.math.BigDecimal;
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
 * @since 2022-02-27
 */
@Data
@TableName("membership_card")
@ApiModel(value="MembershipCard对象", description="")
public class MembershipCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员卡id")
    @TableId(value = "membership_card_id", type = IdType.AUTO)
    private Integer membershipCardId;

    @ApiModelProperty(value = "会员卡名称")
    private String membershipCardName;

    @ApiModelProperty(value = "会员卡价格")
    private BigDecimal membershipCardPrice;

    @ApiModelProperty(value = "场馆id")
    private Integer placeId;

}
