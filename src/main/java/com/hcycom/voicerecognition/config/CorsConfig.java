package com.hcycom.voicerecognition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域
 * @author Silence
 * @create 2019-04-04 20:24
 */
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig(){
        //1.添加CORS配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //放行哪些原始域
        corsConfiguration.addAllowedOrigin("*");
        //是否发送Cookie信息
        corsConfiguration.setAllowCredentials(true);
        //放行哪些原始域（请求方式）
        corsConfiguration.addAllowedMethod("*");
        //放行哪些原始域（头部信息）
        corsConfiguration.addAllowedHeader("*");
        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        corsConfiguration.addExposedHeader("content-type");
        return  corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //2.添加映射路径，配置所有请求
        source.registerCorsConfiguration("/**", buildConfig());
        //3.返回新的CorFilter
        return new CorsFilter(source);
    }
}
