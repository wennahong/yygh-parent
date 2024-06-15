package com.iteva.yygh.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.iteva")
public class CmnServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmnServiceApplication.class, args);
    }
}
