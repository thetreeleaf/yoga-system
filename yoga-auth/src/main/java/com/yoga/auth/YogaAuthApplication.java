package com.yoga.auth;


import com.yoga.api.feign.MemberFeignClient;
import com.yoga.api.feign.OAuthClientFeignClient;
import com.yoga.api.feign.UserFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.yoga")
@EnableFeignClients(basePackageClasses = {UserFeignClient.class, MemberFeignClient.class})
public class YogaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(YogaAuthApplication.class, args);
    }

}

