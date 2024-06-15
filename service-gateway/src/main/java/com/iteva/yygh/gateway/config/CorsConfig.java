package com.iteva.yygh.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*"); // 设置允许的来源，可以是具体的域名或 "*" 表示允许所有来源
        corsConfig.addAllowedMethod("*"); // 设置允许的请求方法，如 GET、POST、PUT 等
        corsConfig.addAllowedHeader("*"); // 设置允许的请求头，如 Content-Type、Authorization 等
        corsConfig.setAllowCredentials(true); // 设置是否允许发送 Cookie

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", corsConfig); // 对所有路径应用跨域配置

        return new CorsWebFilter(source);
    }
}
