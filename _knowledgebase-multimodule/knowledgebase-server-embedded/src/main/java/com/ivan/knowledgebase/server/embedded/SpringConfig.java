package com.ivan.knowledgebase.server.embedded;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:applicationContext.xml"})
@ComponentScan(basePackages = {
        "com.ivan.knowledgebase.server.embedded.resource",
        "com.ivan.knowledgebase.server.embedded.service"})
public class SpringConfig {
}