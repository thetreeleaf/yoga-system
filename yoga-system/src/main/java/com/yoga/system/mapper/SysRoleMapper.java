package com.yoga.system.mapper;

import com.yoga.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Integer getRoleNum();
}
