package com.example.jwtdemo.config;

import com.example.jwtdemo.interceptor.JWTInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Magic
 * @version V1.0.0
 * @Description:拦截器配置类
 * @date 2021/2/17 14:50
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    /**
     * 在容器启动的时候执行
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("jwtInterceptor init!!!");
        JWTInterceptor jwtInterceptor = new JWTInterceptor();
        List<String> pathPatterns = new ArrayList<>();
        pathPatterns.add("/portal/**");
        registry.addInterceptor(jwtInterceptor).addPathPatterns(pathPatterns);
    }
}
