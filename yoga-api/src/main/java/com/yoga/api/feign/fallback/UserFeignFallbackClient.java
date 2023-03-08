package com.yoga.api.feign.fallback;

import com.yoga.api.feign.UserFeignClient;
import com.yoga.common.result.Result;
import com.yoga.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jisoo
 */
@Component
@Slf4j
public class UserFeignFallbackClient implements UserFeignClient {

    @Override
    public Result getAuthInfoByUsername(String username) {
        log.error("feign远程调用系统用户服务异常后的降级方法");

        return Result.failed(ResultCode.DEGRADATION);
    }
}
