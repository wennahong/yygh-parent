package com.iteva.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.iteva.yygh.user.mapper")
public class MybatisPlusConfig {
}
