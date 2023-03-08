package com.yoga.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yoga.system.entity.SysRole;
import com.yoga.system.entity.SysRoleMenu;
import com.yoga.system.entity.SysRolePermission;
import com.yoga.system.entity.SysUserRole;
import com.yoga.system.mapper.SysRoleMapper;
import com.yoga.system.service.SysRoleMenuService;
import com.yoga.system.service.SysRolePermissionService;
import com.yoga.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.system.service.SysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private SysRoleMenuService sysRoleMenuService;
    private SysUserRoleService sysUserRoleService;
    private SysRolePermissionService sysRolePermissionService;


    @Override
    public boolean delete(List<Long> ids) {
        Optional.ofNullable(ids).orElse(new ArrayList<>()).forEach(id -> {
            int count = sysUserRoleService.count(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
            Assert.isTrue(count <= 0, "该角色已分配用户，无法删除");
            sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
            sysRolePermissionService.remove(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, id));
        });
        return this.removeByIds(ids);
    }

    @Override
    public Integer getRoleNum() {
        Integer i = this.baseMapper.getRoleNum();
        return i;
    }
}
