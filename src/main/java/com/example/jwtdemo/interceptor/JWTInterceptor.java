package com.example.jwtdemo.interceptor;

import com.example.jwtdemo.filter.JWTCheckFilter;
import com.example.jwtdemo.jwt.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于拦截器实现token校验
 * @Date 2021-02-17 14:28:00
 *
 */
public class JWTInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(JWTInterceptor.class);

    /**
     *有匹配的url时执行，在执行业务逻辑前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle------>");
        String jws = request.getHeader("authorization");
        logger.info("JWTInterceptor check jws --> {}",jws);
        Auth.parseJws(jws);
        return true;
    }


    /**
     * 有匹配的url时执行，在执行业务逻辑后执行，且只有当preHandle方法返回true时才会执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle------>");
    }


    /**
     * 有匹配的url时执行，在postHandle方法执行后执行，只有当preHandle方法返回true时才会执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("afterCompletion------>");
    }
}
