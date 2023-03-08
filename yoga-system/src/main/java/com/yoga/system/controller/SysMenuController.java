package com.yoga.system.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yoga.common.result.Result;
import com.yoga.system.entity.SysMenu;
import com.yoga.system.service.SysMenuService;
import com.yoga.system.service.SysPermissionService;
import com.yoga.system.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/sysMenu")
@RequiredArgsConstructor
@Slf4j
public class SysMenuController {

    private final SysMenuService menuService;
    private final SysPermissionService permissionService;

    @ApiOperation(value = "菜单表格（Table）层级列表")
    @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataType = "String")
    @GetMapping("/table")
    public Result getTableList(String name) {
        List<MenuVO> menuList = menuService.listTable(name);
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单下拉（Select）层级列表")
    @GetMapping("/select")
    public Result getSelectList() {
        List<ValueLabelVO> menuList = menuService.listSelect();
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单树形（TreeSelect）层级列表")
    @GetMapping("/tree_select")
    public Result getTreeSelectList() {
        List<IdLabelVO> menuList = menuService.listTreeSelect();
        return Result.success(menuList);
    }

    @ApiOperation(value = "菜单路由（Route）层级列表")
    @GetMapping("/route")
    public Result getRouteList() {
        List<RouteVO> routeList = menuService.listRoute();
        return Result.success(routeList);
    }

    @ApiOperation(value = "菜单详情")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysMenu menu = menuService.getById(id);
        return Result.success(menu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping
    public Result addMenu(@RequestBody SysMenu menu) {
        boolean result = menuService.saveMenu(menu);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping(value = "/{id}")
    public Result updateMenu(
            @ApiParam("菜单ID") @PathVariable Long id,
            @RequestBody SysMenu menu
    ) {
        boolean result = menuService.updateMenu(menu);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{ids}")
    public Result delete(
            @ApiParam("菜单ID，多个以英文(,)分割")  @PathVariable("ids") String ids) {
        boolean result = menuService.removeByIds(Arrays.asList(ids.split(",")));
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改菜单")
    @PatchMapping(value = "/{id}")
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public Result patch(@PathVariable Integer id, @RequestBody SysMenu menu) {
        LambdaUpdateWrapper<SysMenu> updateWrapper = new LambdaUpdateWrapper<SysMenu>().eq(SysMenu::getId, id);
        updateWrapper.set(menu.getVisible() != null, SysMenu::getVisible, menu.getVisible());
        boolean result = menuService.update(updateWrapper);
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }


    @ApiOperation(value = "服务列表")
    @GetMapping("")
    public Result microServiceList() {
        List<ServiceNameVo> serviceList = menuService.getMicroService();
        return Result.success(serviceList);
    }
}

