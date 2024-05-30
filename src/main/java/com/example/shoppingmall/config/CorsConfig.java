package com.example.shoppingmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 인증된 사용자의 자격 증명이 요청과 함께 전송되어야 함을 허용
        config.addAllowedOrigin("http://localhost:3000"); // 요청을 허용할 오리진
        config.addAllowedHeader("*"); // 요청을 허용할 헤더
        config.addAllowedMethod("*"); // 요청을 허용할 HTTP 메소드
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
