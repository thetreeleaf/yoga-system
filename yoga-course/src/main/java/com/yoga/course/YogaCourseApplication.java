package com.yoga.course;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.yoga")
@EnableTransactionManagement
public class YogaCourseApplication {

    public static void main(String[] args) {

        SpringApplication.run(YogaCourseApplication.class, args);
    }
}
