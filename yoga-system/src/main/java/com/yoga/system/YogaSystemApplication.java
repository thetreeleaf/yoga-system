package com.yoga.system;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.yoga")
public class YogaSystemApplication {


    public static void main(String[] args) {

        SpringApplication.run(YogaSystemApplication.class, args);
    }

}
