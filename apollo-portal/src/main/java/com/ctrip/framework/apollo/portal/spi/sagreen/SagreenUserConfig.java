package com.ctrip.framework.apollo.portal.spi.sagreen;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="sagreen")
public class SagreenUserConfig {

    private String key ;
    private String ivParameter ;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIvParameter() {
        return ivParameter;
    }

    public void setIvParameter(String ivParameter) {
        this.ivParameter = ivParameter;
    }
}