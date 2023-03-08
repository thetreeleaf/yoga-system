package com.yoga.api.feign;

import com.yoga.api.dto.AuthClientDTO;
import com.yoga.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "yoga-system", contextId = "oauth-client")
public interface OAuthClientFeignClient {

    @PostMapping("/system/sysOauthClient/getOAuth2ClientById")
    Result<AuthClientDTO> getOAuth2ClientById(@RequestParam(value = "clientId") String clientId);
}
