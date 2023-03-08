package com.yoga.api.feign;

import com.yoga.api.dto.AuthUserDTO;
import com.yoga.api.feign.fallback.UserFeignFallbackClient;
import com.yoga.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "yoga-system", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @PostMapping("/system/sysUser/username/{username}")
    Result<AuthUserDTO> getAuthInfoByUsername(@PathVariable(value = "username") String username);
}
