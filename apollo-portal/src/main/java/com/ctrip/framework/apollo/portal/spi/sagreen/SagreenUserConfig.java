package com.ctrip.framework.apollo.portal.spi.sagreen;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="sagreen")
public class SagreenUserConfig {

    private String key = "jw%9h@7M%NF*OEVv";
    private String ivParameter ="6v&d3OtxqrZVAw!9";


    public String getKey() {
        return key;
    }

    public String getIvParameter() {
        return ivParameter;
    }

}