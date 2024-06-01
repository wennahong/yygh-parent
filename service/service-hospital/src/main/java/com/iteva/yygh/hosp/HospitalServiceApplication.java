package com.iteva.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.iteva")
public class HospitalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalServiceApplication.class, args);
    }
}
