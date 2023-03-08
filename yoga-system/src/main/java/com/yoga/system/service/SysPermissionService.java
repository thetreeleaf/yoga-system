package com.yoga.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.system.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.system.query.PermissionPageQuery;
import com.yoga.system.vo.PermissionPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> listPermRoles();

    /**
     * 根据角色编码集合获取按钮权限标识
     *
     * @param roles 角色权限编码集合
     * @return
     */
    List<String> listBtnPermByRoles(@Param("roles") List<String> roles);

    /**
     * 刷新Redis缓存中角色菜单的权限规则，角色和菜单信息变更调用
     */
    boolean refreshPermRolesRules();

    /**
     * 获取权限分页列表
     *
     * @param permissionPageQuery
     * @return
     */
    IPage<PermissionPageVO> listPermissionsWithPage(@Param("queryParams") PermissionPageQuery permissionPageQuery);
}
