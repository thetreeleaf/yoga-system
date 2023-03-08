package com.yoga.system.service;

import com.yoga.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
public interface SysRoleService extends IService<SysRole> {

    boolean delete(List<Long> ids);

    Integer getRoleNum();
}
