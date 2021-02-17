package com.example.jwtdemo.config;

import com.example.jwtdemo.filter.JWTCheckFilter;
import com.example.jwtdemo.user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final static Logger logger = LoggerFactory.getLogger(FilterConfig.class);

    /**
     * 可以通过配置的方式配置过滤器，另一种方式是使用注解@WebFilter和@ServletComponentScan配合实现
     * @return
     */
    /*@Bean
    public FilterRegistrationBean filterRegistrationBean(){
        logger.info("JWTCheckFilter config");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JWTCheckFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/portal/*");
        filterRegistrationBean.setName("JWTCheckFilter");
        return filterRegistrationBean;
    }*/
}
