package com.ivan.knowledgebase.server.embedded.service;

public class SpanishGreetingService implements GreetingService {
    @Override
    public String getGreeting(String name) {
        return "Hola, " + name + "!";
    }
}