package com.yoga.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@TableName("sys_role_permission")
@ApiModel(value="SysRolePermission对象", description="角色权限表")
@Data
@AllArgsConstructor
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "资源id")
    private Long permissionId;

}
