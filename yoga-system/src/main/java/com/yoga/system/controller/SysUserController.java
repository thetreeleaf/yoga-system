package com.yoga.system.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.api.dto.AuthUserDTO;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.common.result.ResultCode;
import com.yoga.common.web.util.UserUtils;
import com.yoga.system.entity.Place;
import com.yoga.system.entity.SysUser;
import com.yoga.system.query.UserPageQuery;
import com.yoga.system.service.PlaceService;
import com.yoga.system.service.SysPermissionService;
import com.yoga.system.service.SysUserService;
import com.yoga.system.vo.LoginUserVO;
import com.yoga.system.vo.RegisterVO;
import com.yoga.system.vo.UserFormVO;
import com.yoga.system.vo.UserPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/sysUser")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;
    private final SysPermissionService sysPermissionService;
    private final PlaceService placeService;

    @ApiOperation(value = "用户分页列表")
    @GetMapping("/page")
    public PageResult<UserPageVO> listUsersWithPage(
            UserPageQuery queryParams
    ) {
        IPage<UserPageVO> result = sysUserService.listUsersWithPage(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "获取用户表单详情")
    @GetMapping("/{userId}/form_detail")
    public Result<UserFormVO> getUserDetail(
            @ApiParam(value = "用户ID", example = "1") @PathVariable Long userId
    ) {
        UserFormVO userDetail = sysUserService.getUserFormDetail(userId);
        return Result.success(userDetail);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping
    public Result addUser(@RequestBody SysUser user) {
        boolean result = sysUserService.saveUser(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/regist")
    public Result addUser(@RequestBody RegisterVO registerVO) {
        Place place = new Place();
        BeanUtils.copyProperties(registerVO,place);
        place.setDeleted(0);
        boolean save = placeService.save(place);
        if (save) {
            SysUser sysUser = new SysUser();
            sysUser.setDeleted(0);
            sysUser.setUsername(registerVO.getUsername());
            sysUser.setMobile(registerVO.getPhone());
            sysUser.setStatus(1);
            sysUser.setEmail(registerVO.getEmail());
            sysUser.setPlaceId(Long.valueOf(place.getId()));
            save = sysUserService.saveUser(sysUser);
        }
        return Result.judge(save);
    }


    @ApiOperation(value = "修改用户角色")
    @PutMapping(value = "/{userId}")
    public Result updateUserRole(
            @ApiParam("用户ID") @PathVariable Long userId,
            @RequestBody SysUser user
    ) {
        boolean result = sysUserService.updateUser(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/update/user/{userId}")
    public Result updateUser(
            @ApiParam("用户ID") @PathVariable Long userId,
            @RequestBody SysUser user
    ) {
        boolean result = sysUserService.updateById(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping(value = "/update/password/{userId}")
    public Result updatePassword(
            @ApiParam("用户ID") @PathVariable Long userId,
            @Param("oldPassword") String oldPassword,@Param("newPassword") String newPassword
    ) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        boolean result = false;
        if (passwordEncoder.matches(oldPassword,sysUser.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(newPassword));
            result = sysUserService.updateById(sysUser);
            return Result.judge(result);
        }
        return Result.failed(ResultCode.OLDPASSWORD_ERROR);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{ids}")
    public Result deleteUsers(
            @ApiParam("用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = sysUserService.removeByIds(Arrays.asList(ids.split(",")).stream().collect(Collectors.toList()));
        return Result.judge(status);
    }

    @ApiOperation(value = "选择性更新用户")
    @PatchMapping(value = "/{userId}")
    public Result updateUserPart(
            @ApiParam("用户ID") @PathVariable Long userId,
            @RequestBody SysUser user
    ) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId);
        updateWrapper.set(user.getStatus() != null, SysUser::getStatus, user.getStatus());
        updateWrapper.set(user.getPassword() != null, SysUser::getPassword,
                user.getPassword() != null ? passwordEncoder.encode(user.getPassword())
                        : null);
        boolean status = sysUserService.update(updateWrapper);
        return Result.judge(status);
    }

    /**
     * 提供用于用户登录认证信息
     */
    @ApiOperation(value = "根据用户名获取认证信息")
    @PostMapping("/username/{username}")
    public Result<AuthUserDTO> getAuthInfoByUsername(
            @ApiParam("用户名") @PathVariable String username) {
        AuthUserDTO user = sysUserService.getAuthInfoByUsername(username);
        return Result.success(user);
    }

    @ApiOperation(value = "获取当前登陆的用户信息")
    @GetMapping("/me")
    public Result<LoginUserVO> getCurrentUser() {
        LoginUserVO loginUserVO = new LoginUserVO();
        // 用户基本信息
        Long userId = UserUtils.getUserId();
        SysUser user = sysUserService.getById(userId);
        loginUserVO.setUserId(user.getId());
        BeanUtil.copyProperties(user, loginUserVO);
        // 用户角色信息
        List<String> roles = UserUtils.getRoles();
        loginUserVO.setRoles(roles);
        // 用户按钮权限信息
        List<String> perms = sysPermissionService.listBtnPermByRoles(roles);
        loginUserVO.setPerms(perms);
        return Result.success(loginUserVO);
    }

    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/get/{userId}")
    public Result<SysUser> getUserProfile(@ApiParam("用户id") @PathVariable Long userId) {
        SysUser sysUser = sysUserService.getById(userId);
        return Result.success(sysUser);
    }

    @ApiOperation(value = "获取后台用户数量")
    @GetMapping("/num")
    public Result getUserNum() {
        Integer num = sysUserService.getUserNum();
        return Result.success(num);
    }
}

