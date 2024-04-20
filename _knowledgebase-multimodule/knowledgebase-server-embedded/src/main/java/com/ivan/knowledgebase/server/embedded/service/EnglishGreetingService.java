package com.ivan.knowledgebase.server.embedded.service;

import org.springframework.stereotype.Service;

@Service
public class EnglishGreetingService implements GreetingService {
    @Override
    public String getGreeting(String name) {
        return "Hello, " + name + "!";
    }
}