package com.fri;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan(basePackages="com.fri.dao")
public class CheckenteringServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckenteringServiceApplication.class, args);
    }

}
