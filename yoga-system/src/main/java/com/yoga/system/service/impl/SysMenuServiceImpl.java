package com.yoga.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yoga.common.constant.GlobalConstants;
import com.yoga.system.constant.SystemConstants;
import com.yoga.system.entity.SysMenu;
import com.yoga.system.mapper.SysMenuMapper;
import com.yoga.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.system.service.SysPermissionService;
import com.yoga.system.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    private final SysPermissionService permissionService;

    /**
     * 菜单表格（Table）层级列表
     *
     * @param name 菜单名称
     * @return
     */
    @Override
    public List<MenuVO> listTable(String name) {
        List<SysMenu> menuList = this.list(
                new LambdaQueryWrapper<SysMenu>()
                        .like(StrUtil.isNotBlank(name), SysMenu::getName, name)
                        .orderByAsc(SysMenu::getSort)
        );
        return recursion(menuList);
    }

    /**
     * 递归生成菜单表格层级列表
     *
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private static List<MenuVO> recursion(List<SysMenu> menuList) {
        List<MenuVO> menuTableList = new ArrayList<>();
        // 保存所有节点的 id
        Set<Long> nodeIdSet = menuList.stream()
                .map(SysMenu::getId)
                .collect(Collectors.toSet());
        for (SysMenu sysMenu : menuList) {
            // 不在节点 id 集合中存在的 id 即为顶级节点 id, 递归生成列表
            Long parentId = sysMenu.getParentId();
            if (!nodeIdSet.contains(parentId)) {
                menuTableList.addAll(recursionTableList(parentId, menuList));
                nodeIdSet.add(parentId);
            }
        }
        // 如果结果列表为空说明所有的节点都是独立分散的, 直接转换后返回
        if (menuTableList.isEmpty()) {
            return menuList.stream()
                    .map(item -> {
                        MenuVO menuVO = new MenuVO();
                        BeanUtil.copyProperties(item, menuVO);
                        return menuVO;
                    })
                    .collect(Collectors.toList());
        }
        return menuTableList;
    }

    /**
     * 递归生成菜单表格层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<MenuVO> recursionTableList(Long parentId, List<SysMenu> menuList) {
        List<MenuVO> menuTableList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    MenuVO menuVO = new MenuVO();
                    BeanUtil.copyProperties(menu, menuVO);
                    List<MenuVO> children = recursionTableList(menu.getId(), menuList);

                    if (CollectionUtil.isNotEmpty(children)) {
                        menuVO.setChildren(children);
                    }
                    menuTableList.add(menuVO);
                });
        return menuTableList;
    }


    /**
     * 菜单下拉（Select）层级列表
     *
     * @return
     */
    @Override
    public List<ValueLabelVO> listSelect() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<ValueLabelVO> menuSelectList = recursionSelectList(SystemConstants.ROOT_MENU_ID, menuList);
        return menuSelectList;
    }


    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<ValueLabelVO> recursionSelectList(Long parentId, List<SysMenu> menuList) {
        List<ValueLabelVO> menuSelectList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    ValueLabelVO valueLabelVO = new ValueLabelVO(menu.getId(), menu.getName());
                    List<ValueLabelVO> children = recursionSelectList(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        valueLabelVO.setChildren(children);
                    }
                    menuSelectList.add(valueLabelVO);
                });
        return menuSelectList;
    }


    /**
     * 菜单路由（Route）层级列表
     * <p>
     *
     *
     * @return
     * @Cacheable cacheNames:缓存名称，不同缓存的数据是彼此隔离； key: 缓存Key。
     */
    @Override
    public List<RouteVO> listRoute() {
        List<SysMenu> menuList = this.baseMapper.listRoutes();
        List<RouteVO> list = recursionRoute(SystemConstants.ROOT_MENU_ID, menuList);
        return list;
    }


    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<RouteVO> recursionRoute(Long parentId, List<SysMenu> menuList) {
        List<RouteVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouteVO routeVO = new RouteVO();
                    routeVO.setName(menu.getId() + ""); // 根据name路由跳转 this.$router.push({path:xxx})
                    routeVO.setPath(menu.getPath());    // 根据path路由跳转 this.$router.push({name:xxx})
                    routeVO.setRedirect(menu.getRedirect());
                    routeVO.setComponent(menu.getComponent());
                    routeVO.setRedirect(menu.getRedirect());
                    RouteVO.Meta meta = new RouteVO.Meta(menu.getName(), menu.getIcon(), menu.getRoles());
                    routeVO.setMeta(meta);
                    // 菜单显示隐藏
                    routeVO.setHidden(GlobalConstants.STATUS_YES.equals(menu.getVisible()));
                    List<RouteVO> children = recursionRoute(menu.getId(), menuList);
                    routeVO.setChildren(children);
                    if (CollectionUtil.isNotEmpty(children)) {
                        routeVO.setAlwaysShow(Boolean.TRUE);
                    }
                    list.add(routeVO);
                }));
        return list;

    }

    /**
     * 菜单下拉（TreeSelect）层级列表
     *
     * @return
     */
    @Override
    public List<IdLabelVO> listTreeSelect() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<IdLabelVO> menuSelectList = recursionTreeSelectList(SystemConstants.ROOT_MENU_ID, menuList);
        return menuSelectList;
    }

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean saveMenu(SysMenu menu) {
        String component = menu.getComponent();
        String path = menu.getPath();

        if (StrUtil.isBlank(path)) { // 非外链
//            if ("Layout".equals(component)) {
//                menu.setPath("/" + IdUtil.simpleUUID());
//            } else {
                String[] split = component.split("/");
                menu.setPath(split[1]);
//            }
        } else {
            menu.setPath(path);
        }
        LocalDateTime localDateTime = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        menu.setGmtCreate(localDateTime);
        menu.setGmtModified(localDateTime);
        boolean result = this.save(menu);
        if (result == true) {
            permissionService.refreshPermRolesRules();
        }
        return result;
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean updateMenu(SysMenu menu) {
        String component = menu.getComponent();
        String path = menu.getPath();
        // 检测页面路径是否变化
        SysMenu oldMenu = this.getById(menu.getId());
        if (oldMenu.getComponent() != null && !oldMenu.getComponent().equals(component)) {
            if ("Layout".equals(component)) {
                menu.setPath(path);
            } else {
                String[] split = component.split("/");
                menu.setPath(split[1]);
            }
        }
        LocalDateTime localDateTime = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        menu.setGmtModified(localDateTime);
        boolean result = this.updateById(menu);
        if (result == true) {
            permissionService.refreshPermRolesRules();
        }
        return result;
    }

    /**
     * 递归生成菜单下拉(TreeSelect)层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<IdLabelVO> recursionTreeSelectList(Long parentId, List<SysMenu> menuList) {
        List<IdLabelVO> menuSelectList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    IdLabelVO idLabelVO = new IdLabelVO(menu.getId(), menu.getName());
                    List<IdLabelVO> children = recursionTreeSelectList(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        idLabelVO.setChildren(children);
                    }
                    menuSelectList.add(idLabelVO);
                });
        return menuSelectList;
    }


    /**
     * 清理路由缓存
     */
    @Override
    public void cleanCache() {
    }

    /**
     * 获取路由列表(适配Vue3的vue-next-router)
     *
     * @return
     */
    @Override
    public List<NextRouteVO> listNextRoutes() {
        List<SysMenu> menuList = this.baseMapper.listRoutes();
        List<NextRouteVO> list = recursionNextRoute(SystemConstants.ROOT_MENU_ID, menuList);
        return list;
    }

    @Override
    public List<ServiceNameVo> getMicroService() {
        List<ServiceNameVo> menuList = this.baseMapper.getMicroService();
        return menuList;
    }


    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<NextRouteVO> recursionNextRoute(Long parentId, List<SysMenu> menuList) {
        List<NextRouteVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    NextRouteVO nextRouteVO = new NextRouteVO();
                    nextRouteVO.setName(menu.getId() + ""); // 根据name路由跳转 this.$router.push({path:xxx})
                    nextRouteVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({name:xxx})
                    nextRouteVO.setRedirect(menu.getRedirect());
                    nextRouteVO.setComponent(menu.getComponent());
                    nextRouteVO.setRedirect(menu.getRedirect());

                    NextRouteVO.Meta meta = new NextRouteVO.Meta();
                    meta.setTitle(menu.getName());
                    meta.setIcon(menu.getIcon());
                    meta.setRoles(menu.getRoles());
                    meta.setHidden(!GlobalConstants.STATUS_YES.equals(menu.getVisible()));

                    nextRouteVO.setMeta(meta);
                    List<NextRouteVO> children = recursionNextRoute(menu.getId(), menuList);
                    nextRouteVO.setChildren(children);
                    list.add(nextRouteVO);
                }));
        return list;
    }
}
