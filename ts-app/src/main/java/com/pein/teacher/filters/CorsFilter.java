package com.pein.teacher.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  
 *  跨域过滤器 
 * @author meng 
 * @version  
 * @since 2016年6月19日 
 */  
@Component
public class CorsFilter implements Filter {
  
    private final static Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);
  

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");  
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");  
        chain.doFilter(req, res);
    }  
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}  
}  