package com.yoga.api.feign;

import com.yoga.api.dto.MemberAuthInfoDTO;
import com.yoga.api.dto.MemberDTO;
import com.yoga.common.result.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "yoga-member", contextId = "member")
public interface MemberFeignClient {

    /**
     * 新增会员
     *
     * @param member
     * @return
     */
    @PostMapping("/member/member")
    Result<Long> addMember(@RequestBody MemberDTO member);


    /**
     * 获取会员的 openid
     *
     * @return
     */
    @PostMapping("/member/member/{memberId}/openid")
    Result<String> getMemberOpenId(@PathVariable(value = "memberId") Long memberId);


    /**
     * 根据openId获取会员认证信息
     *
     * @param openid
     * @return
     */
    @GetMapping("/member/member/openid/{openid}")
    Result<MemberAuthInfoDTO> loadUserByOpenId(@PathVariable(value = "openid") String openid);


}
