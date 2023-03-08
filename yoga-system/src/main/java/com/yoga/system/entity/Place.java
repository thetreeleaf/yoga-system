package com.yoga.system.entity;

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
 * @since 2022-03-05
 */
@Data
@TableName("place")
@ApiModel(value="Place对象", description="")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场馆编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "场馆名称")
    private String placeName;

    @ApiModelProperty(value = "场馆地址")
    private String address;

    @ApiModelProperty(value = "场馆号码")
    private String phone;

    @ApiModelProperty(value = "场馆图片")
    private String placePicture;

    @ApiModelProperty(value = "逻辑删除标识：0-未删除；1-已删除")
    private Integer deleted;

}
