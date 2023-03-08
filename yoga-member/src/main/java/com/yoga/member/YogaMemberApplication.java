package com.yoga.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.yoga")
public class YogaMemberApplication {

    public static void main(String[] args) {

        SpringApplication.run(YogaMemberApplication.class, args);
    }
}
