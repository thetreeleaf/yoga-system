package com.yoga.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 会员认证传输层对象
 *
 */
@Data
@Accessors(chain = true)
public class MemberAuthInfoDTO {

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 会员名(openId)
     */
    private String username;

    /**
     * 状态(1:正常；0：禁用)
     */
    private Integer status;
}
