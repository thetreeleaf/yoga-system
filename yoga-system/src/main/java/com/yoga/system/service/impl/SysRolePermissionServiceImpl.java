package com.yoga.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yoga.system.entity.SysRolePermission;
import com.yoga.system.form.RolePermsForm;
import com.yoga.system.mapper.SysRolePermissionMapper;
import com.yoga.system.service.SysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    /**
     * 根据菜单ID和角色ID获取权限ID集合
     *
     * @param menuId
     * @param roleId
     * @return
     */
    @Override
    public List<Long> listPermIds(@Param("menuId") Long menuId,@Param("roleId") Long roleId) {
        return this.baseMapper.listPermIds(menuId, roleId);
    }

    /**
     * 保存角色权限
     *
     * @return
     */
    @Override
    public boolean saveRolePerms(RolePermsForm rolePermsForm) {

        Long menuId = rolePermsForm.getMenuId();
        Long roleId = rolePermsForm.getRoleId();
        List<Long> permIds = rolePermsForm.getPermIds();

        List<Long> oldPermIds = this.listPermIds(menuId, roleId);

        // 验证权限数据是否改变
        List<Long> sortedPermIds = permIds.stream().sorted().collect(Collectors.toList());
        List<Long> sortedOldPermIds = oldPermIds.stream().sorted().collect(Collectors.toList());
        boolean permDataChangeFlag = !CollectionUtil.isEqualList(sortedPermIds, sortedOldPermIds);
        Assert.isTrue(permDataChangeFlag, "提交失败，权限数据无改动！");

        // 删除此次保存移除的权限
        boolean updateFlag = false;
        if (CollectionUtil.isNotEmpty(oldPermIds)) {
            List<Long> removePermIds = oldPermIds.stream().filter(id -> !permIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removePermIds)) {
                updateFlag = this.remove(new LambdaQueryWrapper<SysRolePermission>()
                        .eq(SysRolePermission::getRoleId, roleId)
                        .in(SysRolePermission::getPermissionId, removePermIds));
            }
        }

        // 新增数据库不存在的权限
        if (CollectionUtil.isNotEmpty(permIds)) {
            List<Long> newPermIds = permIds.stream().filter(id -> !oldPermIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(newPermIds)) {
                List<SysRolePermission> rolePerms = new ArrayList<>();
                for (Long permId : newPermIds) {
                    SysRolePermission rolePerm = new SysRolePermission(roleId, permId);
                    rolePerms.add(rolePerm);
                }
                updateFlag = this.saveBatch(rolePerms);
            }
        }
        return updateFlag;
    }

}
