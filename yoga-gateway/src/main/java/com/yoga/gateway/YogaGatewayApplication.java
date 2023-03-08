package com.yoga.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.yoga"})
public class YogaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(YogaGatewayApplication.class, args);
    }

}

