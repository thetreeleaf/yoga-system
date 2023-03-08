package com.yoga.system.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场馆分页视图对象
 *
 */
@ApiModel("场馆分页视图对象")
@Data
public class PlacePageVO {

    @ApiModelProperty(value = "场馆编号")
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
