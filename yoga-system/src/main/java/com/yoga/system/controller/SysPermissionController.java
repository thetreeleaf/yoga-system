package com.yoga.system.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.system.entity.SysPermission;
import com.yoga.system.query.PermissionPageQuery;
import com.yoga.system.service.SysPermissionService;
import com.yoga.system.vo.PermissionPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Api(tags = "权限接口")
@RestController
@RequestMapping("/sysPermission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService SysPermissionService;

    @ApiOperation(value = "列表分页")
    @GetMapping("/page")
    public PageResult<PermissionPageVO> listPermissionsWithPage(
            PermissionPageQuery permissionPageQuery
    ) {
        IPage<PermissionPageVO> result = SysPermissionService.listPermissionsWithPage(permissionPageQuery);
        return PageResult.success(result);
    }

    @ApiOperation(value = "权限列表")
    @GetMapping
    public Result listPermissions(
            @ApiParam(value = "菜单ID") @RequestParam(required = false) Long menuId
    ) {
        List<SysPermission> list = SysPermissionService.list(
                new LambdaQueryWrapper<SysPermission>()
                        .eq(menuId != null, SysPermission::getMenuId, menuId)
        );
        return Result.success(list);
    }

    @ApiOperation(value = "权限详情")
    @GetMapping("/{permissionId}")
    public Result getPermissionDetail(
            @ApiParam("权限ID") @PathVariable Long permissionId
    ) {
        SysPermission permission = SysPermissionService.getById(permissionId);
        return Result.success(permission);
    }

    @ApiOperation(value = "新增权限")
    @PostMapping
    public Result addPermission(
            @RequestBody SysPermission permission
    ) {
        boolean result = SysPermissionService.save(permission);
        if (result) {
            SysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "修改权限")
    @PutMapping(value = "/{permissionId}")
    public Result updatePermission(
            @ApiParam("权限ID") @PathVariable Long permissionId,
            @RequestBody SysPermission permission
    ) {
        boolean result = SysPermissionService.updateById(permission);
        if (result) {
            SysPermissionService.refreshPermRolesRules();
        }
        return Result.judge(result);
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping("/{ids}")
    public Result deletePermissions(
            @ApiParam("权限ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = SysPermissionService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }


}

