package com.yoga.system.mapper;

import com.yoga.system.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
