package com.yoga.common.enums;

import lombok.Getter;

/**
 * 认证身份标识枚举
 */
public enum AuthenticationIdentityEnum {

    USERNAME("username", "用户名"),
    OPENID("openId", "开放式认证系统唯一身份标识");

    @Getter
    private String value;

    @Getter
    private String label;

    AuthenticationIdentityEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static AuthenticationIdentityEnum getByValue(String value) {
        AuthenticationIdentityEnum authenticationIdentityEnum = null;
        for (AuthenticationIdentityEnum item : values()) {
            if (item.getValue().equals(value)) {
                authenticationIdentityEnum = item;
                continue;
            }
        }
        return authenticationIdentityEnum;
    }

}
