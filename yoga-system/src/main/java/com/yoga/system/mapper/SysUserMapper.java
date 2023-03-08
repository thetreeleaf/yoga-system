package com.yoga.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.api.dto.AuthUserDTO;
import com.yoga.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.system.query.UserPageQuery;
import com.yoga.system.vo.UserFormVO;
import com.yoga.system.vo.UserPageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户分页列表
     *
     * @param pageParam
     * @param queryParams
     * @return
     */
    List<UserPageVO> listUsersWithPage(@Param("pageParam") Page<UserPageVO> pageParam,@Param("queryParams") UserPageQuery queryParams);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    UserFormVO getUserFormDetail(Long userId);

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    AuthUserDTO getAuthInfoByUsername(String username);

    Integer getUserNum();
}
