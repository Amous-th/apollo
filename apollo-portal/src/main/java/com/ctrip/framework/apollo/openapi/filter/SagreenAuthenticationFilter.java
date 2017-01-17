package com.ctrip.framework.apollo.openapi.filter;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.portal.spi.sagreen.SagreenUserConfig;
import com.ctrip.framework.apollo.portal.util.AESCryptUtils;
import com.ctrip.framework.apollo.portal.util.CookieUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SagreenAuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SagreenAuthenticationFilter.class);
    private static final String LOGIN_URL = "/login/login.html";
    private static final String STATIC_RESOURCE_REGEX = ".*\\.(js|html|htm|png|css|woff2)$";

    private SagreenUserConfig sagreenUserConfig ;

    public SagreenAuthenticationFilter(){
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("SagreenAuthenticationFilter init...");
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        sagreenUserConfig = springContext.getBean(SagreenUserConfig.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws
            IOException, ServletException {
        LOGGER.info("SagreenAuthenticationFilter doFilter ...");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String user = CookieUtils.getCookieValue(request,"sys_user");
        String uri = request.getRequestURI();

        if(isStaticResource(uri)){
            chain.doFilter(req, resp);
            return;
        }
        if(StringUtils.isBlank(user) && uri.equals(LOGIN_URL)){
            chain.doFilter(req, resp);
            return;
        }

        if(StringUtils.isBlank(user) && !uri.equals(LOGIN_URL)){
            request.getRequestDispatcher("/login/login.html").forward(request, response);
            return;
        }

        String decrypted = AESCryptUtils.decrypt(sagreenUserConfig.getKey(), sagreenUserConfig
                .getIvParameter(), user);
        if (StringUtils.isBlank(decrypted)) {
            response.getOutputStream().print("error request..");
            return;
        }

        String[] up = decrypted.split("[|]");
        String userName = up[0];
        String password = up[1];
        if(!password.equals(sagreenUserConfig.getPassword(userName))){
            response.getOutputStream().print("error request..");
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        LOGGER.info("SagreenAuthenticationFilter destroy...");
    }

    private boolean isStaticResource(String uri) {
        return !Strings.isNullOrEmpty(uri) && uri.matches(STATIC_RESOURCE_REGEX);
    }
}
