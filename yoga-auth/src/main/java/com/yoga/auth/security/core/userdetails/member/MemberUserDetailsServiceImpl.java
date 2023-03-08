package com.yoga.auth.security.core.userdetails.member;

import com.yoga.api.dto.MemberAuthInfoDTO;
import com.yoga.api.feign.MemberFeignClient;
import com.yoga.common.enums.AuthenticationIdentityEnum;
import com.yoga.common.result.Result;
import com.yoga.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 微信小程序用户认证服务
 */
@Service("memberUserDetailsService")
@Slf4j
@RequiredArgsConstructor
public class MemberUserDetailsServiceImpl implements UserDetailsService {

    private final MemberFeignClient memberFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }


    /**
     * openid 认证方式
     *
     * @param openId
     * @return
     */
    public UserDetails loadUserByOpenId(String openId) {
        MemberUserDetails userDetails = null;
        Result<MemberAuthInfoDTO> result = memberFeignClient.loadUserByOpenId(openId);

        if (Result.isSuccess(result)) {
            MemberAuthInfoDTO member = result.getData();
            if (null != member) {
                userDetails = new MemberUserDetails(member);
                userDetails.setAuthenticationIdentity(AuthenticationIdentityEnum.OPENID.getValue());   // 认证方式:openId
            }
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }
}
