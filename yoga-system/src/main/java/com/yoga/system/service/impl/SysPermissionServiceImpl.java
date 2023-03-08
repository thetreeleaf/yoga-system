package com.yoga.system.service.impl;



import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.common.constant.GlobalConstants;
import com.yoga.redis.service.RedisService;
import com.yoga.system.entity.SysPermission;
import com.yoga.system.mapper.SysPermissionMapper;
import com.yoga.system.query.PermissionPageQuery;
import com.yoga.system.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.system.vo.PermissionPageVO;
import org.apache.ibatis.annotations.Param;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {


    @Autowired
    private RedisService redisService;

    /**
     * 获取权限分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<PermissionPageVO> listPermissionsWithPage(PermissionPageQuery queryParams) {
        Page<PermissionPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<PermissionPageVO> list = this.baseMapper.listPermissionsWithPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * 根据角色编码集合获取按钮权限
     *
     * @param roles 角色权限编码集合
     * @return
     */
    @Override
    public List<String> listBtnPermByRoles(@Param("roles") List<String> roles) {
        List<String> perms = this.baseMapper.listBtnPermByRoles(roles);
        return perms;
    }
    /**
     * 获取权限和拥有权限的角色映射
     *
     * @return
     */
    @Override
    public List<SysPermission> listPermRoles() {
        return this.baseMapper.listPermRoles();
    }

    /**
     * 刷新权限角色缓存
     *
     * @return
     */
    @Override
    public boolean refreshPermRolesRules() {
        redisService.deleteObject(Arrays.asList(GlobalConstants.URL_PERM_ROLES_KEY, GlobalConstants.BTN_PERM_ROLES_KEY));
        List<SysPermission> permissions = this.listPermRoles();
        if (CollectionUtil.isNotEmpty(permissions)) {
            // 初始化URL【权限->角色(集合)】规则
            List<SysPermission> urlPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getUrlPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(urlPermList)) {
                Map<String, List<String>> urlPermRoles = new HashMap<>();
                urlPermList.stream().forEach(item -> {
                    String perm = item.getUrlPerm();
                    List<String> roles = item.getRoles();
                    urlPermRoles.put(perm, roles);
                });
                redisService.setCacheMap(GlobalConstants.URL_PERM_ROLES_KEY, urlPermRoles);
            }
            // 初始化URL【按钮->角色(集合)】规则
            List<SysPermission> btnPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getBtnPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(btnPermList)) {
                Map<String, List<String>> btnPermRoles = MapUtil.newHashMap();
                btnPermList.stream().forEach(item -> {
                    String perm = item.getBtnPerm();
                    List<String> roles = item.getRoles();
                    btnPermRoles.put(perm, roles);
                });
                redisService.setCacheMap(GlobalConstants.BTN_PERM_ROLES_KEY, btnPermRoles);
            }
        }
        return true;
    }
}
