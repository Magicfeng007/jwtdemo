package com.example.jwtdemo.filter;

import com.example.jwtdemo.jwt.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 该@WebFilter注解为Servlet3.0的规范，并非spring boot提供的，所以要想该注解生效
 * 还需要在spring boot启动类上面加@ServletComponetScan注解
 *
 *
 * @WebFilter这个注解并没有指定执行顺序的属性，其执行顺序依赖于Filter的名称，是根据Filter类名（注意不是配置的filter的名字）的字母顺序倒序排列，并且@WebFilter指定的过滤器优先级都高于FilterRegistrationBean配置的过滤器
 */
//@WebFilter(filterName = "JWTCheckFilter",urlPatterns = "/portal/*")
public class JWTCheckFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(JWTCheckFilter.class);

    /**
     * 容器初始化时执行
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        logger.info("FilterName --> {}",filterConfig.getFilterName());

        while (filterConfig.getInitParameterNames().hasMoreElements()){
            String initParamName = filterConfig.getInitParameterNames().nextElement();
            logger.info("initParameter:{}--{}",initParamName
                    ,filterConfig.getInitParameter(initParamName));
        }
    }


    /**
     * 有匹配的url时执行
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        logger.info("-----------ServletRequest----------");
        logger.info("ContentType --> {}",servletRequest.getContentType());
        logger.info("CharacterEncoding --> {}",servletRequest.getCharacterEncoding());
        logger.info("DispatcherType --> {}",servletRequest.getDispatcherType());
        logger.info("Protocol --> {}",servletRequest.getProtocol());
        logger.info("Scheme --> {}",servletRequest.getScheme());

        logger.info("-----------HttpServletRequest----------");
        logger.info("HttpServletMapping --> {}",httpServletRequest.getHttpServletMapping());
//        logger.info("changeSessionId --> {}",httpServletRequest.changeSessionId());
        logger.info("AuthType --> {}",httpServletRequest.getAuthType());
        logger.info("RequestURI --> {}",httpServletRequest.getRequestURI());
        logger.info("Session id --> {}",httpServletRequest.getSession().getId());

        /*while (httpServletRequest.getHeaderNames().hasMoreElements()){
            String headerName = httpServletRequest.getHeaderNames().nextElement();
            logger.info("header --> [{},{}]",headerName,httpServletRequest.getHeader(headerName));
        }*/


        logger.info("PathInfo --> {}",httpServletRequest.getPathInfo());
        logger.info("ContextPath --> {}",httpServletRequest.getContextPath());
        logger.info("Method --> {}",httpServletRequest.getMethod());

        String jws = httpServletRequest.getHeader("authorization");
        logger.info("jws --> {}",jws);
        Auth.parseJws(jws);

        filterChain.doFilter(servletRequest,servletResponse);//请求的所有业务逻辑会在这之后执行

        //请求的所有业务逻辑会在这之前执行
        logger.info("after doFilter --->");

    }


    /**
     * 容器销毁时执行
     */
    @Override
    public void destroy() {
        logger.info("JWTCheckFilter destroy");
    }
}
