package com.ctrip.framework.apollo.portal.spi.sagreen;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="sagreen")
public class SagreenUserConfig {

    private String key = "jw%9h@7M%NF*OEVv";
    private String ivParameter ="6v&d3OtxqrZVAw!9";

    private Map<String, String> user = new HashMap<String, String>();

    public Map<String, String> getUser() {
        return user;
    }

    public String getPassword(String name) {
        return user.get(name);
    }

    public void setUser(Map<String, String> user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public String getIvParameter() {
        return ivParameter;
    }

}