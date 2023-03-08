package com.yoga.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.api.dto.AuthUserDTO;
import com.yoga.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.system.query.UserPageQuery;
import com.yoga.system.vo.UserFormVO;
import com.yoga.system.vo.UserPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 用户分页列表
     *
     * @return
     */
    IPage<UserPageVO> listUsersWithPage(@Param("queryParams") UserPageQuery queryParams);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    boolean saveUser(SysUser user);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    boolean updateUser(SysUser user);

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    AuthUserDTO getAuthInfoByUsername(String username);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    UserFormVO getUserFormDetail(Long userId);

    Integer getUserNum();
}
