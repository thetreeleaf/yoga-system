package com.yoga.common.web.util;

import cn.hutool.json.JSONObject;
import com.yoga.common.constant.SecurityConstants;

/**
 */
public class MemberUtils {

    /**
     * 获取当前登录会员的ID
     *
     * @return
     */
    public static Long getMemberId() {
        Long memberId = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            memberId = jwtPayload.getLong("memberId");
        }
        return memberId;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = JwtUtils.getJwtPayload().getStr(SecurityConstants.USER_NAME_KEY);
        return username;
    }
}
