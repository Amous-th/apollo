package com.ctrip.framework.apollo.portal.spi.ctrip;

import com.ctrip.framework.apollo.portal.spi.ctrip.filters.SagreenAuthenticationFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebContextConfiguration {

 /*   @Autowired
    private PortalConfig portalConfig;
    @Autowired
    private UserInfoHolder userInfoHolder;*/

   /* @Bean
    public ServletContextInitializer servletContextInitializer() {

        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                String loggingServerIP = portalConfig.cloggingUrl();
                String loggingServerPort = portalConfig.cloggingUrl();
                String credisServiceUrl = portalConfig.credisServiceUrl();

                servletContext.setInitParameter("loggingServerIP",
                        Strings.isNullOrEmpty(loggingServerIP) ? "" : loggingServerIP);
                servletContext.setInitParameter("loggingServerPort",
                        Strings.isNullOrEmpty(loggingServerPort) ? "" : loggingServerPort);
                servletContext.setInitParameter("credisServiceUrl",
                        Strings.isNullOrEmpty(credisServiceUrl) ? "" : credisServiceUrl);
            }
        };
    }*/

    @Bean
    public FilterRegistrationBean sagreenAuthenticationFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new SagreenAuthenticationFilter());
        filter.addUrlPatterns("/*");
        filter.setOrder(1);
        return filter;
    }

/*    @Bean
    public FilterRegistrationBean userAccessFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new UserAccessFilter(userInfoHolder));
        filter.addUrlPatterns("*//*");
        filter.setOrder(5);
        return filter;
    }*/

}
