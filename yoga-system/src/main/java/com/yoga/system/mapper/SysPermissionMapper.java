package com.yoga.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.system.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.system.query.PermissionPageQuery;
import com.yoga.system.vo.PermissionPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 获取权限分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    List<PermissionPageVO> listPermissionsWithPage(@Param("page") Page<PermissionPageVO> page,@Param("queryParams") PermissionPageQuery queryParams);

    /**
     * 获取权限和拥有权限的角色映射
     *
     * @return
     */
    List<SysPermission> listPermRoles();

    /**
     * 根据角色编码集合获取按钮权限
     *
     * @param roles
     * @return
     */
    List<String> listBtnPermByRoles(@Param("roles") List<String> roles);

}
