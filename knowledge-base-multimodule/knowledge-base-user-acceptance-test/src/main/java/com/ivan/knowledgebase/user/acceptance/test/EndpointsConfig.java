package com.ivan.knowledgebase.user.acceptance.test;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

final class EndpointsConfig {
    private final Properties systemProperties;

    EndpointsConfig() {
        this.systemProperties = new Properties();
    }

    public String getApiUrl() {
        return getProperty("blackbox.api.url", "http://127.0.0.1:8080/api");
    }

    private String getProperty(String propertyName, String defaultValue, Object... args) {
        String property = systemProperties.getProperty(propertyName);
        return String.format(StringUtils.isEmpty(property) ? defaultValue : property, args);
    }
}
