package com.yoga.system.service;

import com.yoga.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.system.vo.*;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
public interface SysMenuService extends IService<SysMenu> {


    /**
     * 菜单表格(Table)层级列表
     *
     * @param name 菜单名称
     * @return
     */
    List<MenuVO> listTable(String name);


    /**
     * 菜单下拉(Select)层级列表
     *
     * @return
     */
    List<ValueLabelVO> listSelect();


    /**
     * 菜单路由(Route)层级列表
     *
     * @return
     */
    List<RouteVO> listRoute();

    /**
     * 菜单下拉(TreeSelect)层级列表
     *
     * @return
     */
    List<IdLabelVO> listTreeSelect();

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    boolean saveMenu(SysMenu menu);


    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    boolean updateMenu(SysMenu menu);

    /**
     * 清理路由缓存
     */
    void cleanCache();

    /**
     * 获取路由列表(适配Vue3的vue-next-router)
     *
     * @return
     */
    List<NextRouteVO> listNextRoutes();

    List<ServiceNameVo> getMicroService();
}
