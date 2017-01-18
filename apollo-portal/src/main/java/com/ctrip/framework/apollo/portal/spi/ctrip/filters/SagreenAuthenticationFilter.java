package com.ctrip.framework.apollo.portal.spi.ctrip.filters;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.portal.entity.po.User;
import com.ctrip.framework.apollo.portal.service.ProtalUserService;
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

    private ProtalUserService protalUserService;

    public SagreenAuthenticationFilter(){
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("SagreenAuthenticationFilter init...");
        WebApplicationContext springContext =
                WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        sagreenUserConfig = springContext.getBean(SagreenUserConfig.class);
        protalUserService = springContext.getBean(ProtalUserService.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws
            IOException, ServletException {
        LOGGER.debug("SagreenAuthenticationFilter doFilter ...");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();

        if(isStaticResource(uri)){
            chain.doFilter(req, resp);
            return;
        }
        String sysUser = CookieUtils.getCookieValue(request,"sys_user");
        if(StringUtils.isBlank(sysUser) && uri.equals(LOGIN_URL)){
            chain.doFilter(req, resp);
            return;
        }

        if(StringUtils.isBlank(sysUser) && !uri.equals(LOGIN_URL)){
            request.getRequestDispatcher("/login/login.html").forward(request, response);
            return;
        }

        String decrypted = AESCryptUtils.decrypt(sagreenUserConfig.getKey(), sagreenUserConfig
                .getIvParameter(), sysUser);
        if (StringUtils.isBlank(decrypted)) {
            response.getOutputStream().print("error request..");
            return;
        }

        String[] up = decrypted.split("[|]");
        String userName = up[0];
        String password = up[1];
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            response.getOutputStream().print("error request..");
            return;
        }
        User user = protalUserService.findByUserName(userName);
        if(null == user){
            response.getOutputStream().print("error request..");
            return;
        }
        if(!password.equals(user.getPassword())){
            response.getOutputStream().print("error request..");
            return;
        }
        CookieUtils.setLocalUserName(userName);
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        LOGGER.debug("SagreenAuthenticationFilter destroy...");
    }

    private boolean isStaticResource(String uri) {
        return !Strings.isNullOrEmpty(uri) && uri.matches(STATIC_RESOURCE_REGEX);
    }
}
