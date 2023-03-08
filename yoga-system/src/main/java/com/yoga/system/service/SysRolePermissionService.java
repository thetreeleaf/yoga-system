package com.yoga.system.service;

import com.yoga.system.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.system.form.RolePermsForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {


    /**
     * 根据菜单ID和角色ID获取权限ID集合
     *
     * @param menuId
     * @param roleId
     * @return
     */
    List<Long> listPermIds(@Param("menuId") Long menuId,@Param("roleId") Long roleId);


    /**
     * 保存角色的权限
     *
     * @return
     */
    boolean saveRolePerms(RolePermsForm rolePermsForm);

}
