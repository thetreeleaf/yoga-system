package com.yoga.gateway.kaptcha;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import com.yoga.common.constant.SecurityConstants;
import com.yoga.common.result.Result;
import com.yoga.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/**
 *
 */
@Component
@RequiredArgsConstructor
public class CaptchaHandler implements HandlerFunction<ServerResponse> {

    @Resource(name = "captchaProducerMath")
    private Producer producer;

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        // 生成验证码
        String capText = producer.createText();
        String capStr = capText.substring(0, capText.lastIndexOf("@"));
        String code = capText.substring(capText.lastIndexOf("@") + 1);
        BufferedImage image = producer.createImage(capStr);
        // 缓存验证码至Redis
        String uuid = IdUtil.simpleUUID();
        redisService.setCacheObject(SecurityConstants.VALIDATE_CODE_PREFIX + uuid,code,60L,TimeUnit.SECONDS);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return Mono.error(e);
        }

        java.util.Map resultMap = new HashMap<String, String>();
        resultMap.put("uuid", uuid);
        resultMap.put("img", Base64.encode(os.toByteArray()));

        return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(Result.success(resultMap)));
    }
}
