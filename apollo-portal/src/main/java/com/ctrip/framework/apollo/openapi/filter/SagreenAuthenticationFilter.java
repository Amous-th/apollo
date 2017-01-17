package com.ctrip.framework.apollo.openapi.filter;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.portal.spi.sagreen.SagreenUserConfig;
import com.ctrip.framework.apollo.portal.util.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SagreenAuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SagreenAuthenticationFilter.class);
    private static final String LOGIN_URL = "/login/login.html";

    private SagreenUserConfig sagreenUser ;

    public SagreenAuthenticationFilter(){
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("SagreenAuthenticationFilter init...");
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        sagreenUser = springContext.getBean(SagreenUserConfig.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws
            IOException, ServletException {
        LOGGER.info("SagreenAuthenticationFilter doFilter ...");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String user = CookieUtils.getCookieValue(request,"sys_user");

        String uri = request.getRequestURI();

        if(StringUtils.isBlank(user) && !uri.equals(LOGIN_URL)){
            request.getRequestDispatcher("/login/login.html").forward(request, response);
            //response.getOutputStream().print("error request..");
            return;
        }

        Map<String, Object> um =  sagreenUser.getUser();
        System.out.println(um.toString());

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        LOGGER.info("SagreenAuthenticationFilter destroy...");
    }
}
