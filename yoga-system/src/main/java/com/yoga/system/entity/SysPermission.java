package com.yoga.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.yoga.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Data
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel(value="SysPermission对象", description="权限表")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "菜单模块ID	")
    private Integer menuId;

    @ApiModelProperty(value = "URL权限标识")
    private String urlPerm;

    @ApiModelProperty(value = "按钮权限标识")
    private String btnPerm;

    // 有权限的角色编号集合
    @TableField(exist = false)
    private List<String> roles;


}
