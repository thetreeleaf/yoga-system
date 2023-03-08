package com.yoga.gateway.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@ConfigurationProperties(prefix = "security")
@Configuration
@RefreshScope
public class WhileListConfig {

    /**
     * 放行白名单配置，网关不校验此处的白名单
     */
    private List<String> ignoreUrls = new ArrayList<>();

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }
}
