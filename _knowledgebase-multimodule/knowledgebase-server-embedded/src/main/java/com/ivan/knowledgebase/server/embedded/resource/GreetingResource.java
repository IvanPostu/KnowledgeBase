package com.ivan.knowledgebase.server.embedded.resource;

import com.ivan.knowledgebase.server.embedded.service.EnglishGreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Component
@Path("greeting")
@Produces("text/plain")
public class GreetingResource {

    private final EnglishGreetingService engService;

    @Autowired
    public GreetingResource(EnglishGreetingService engService) {
        this.engService = engService;
    }

    @GET
    @Path("/{lang: es|en}/{name}")
    public String getEnGreeting(@PathParam("lang") String lang, @PathParam("name") String name) {
        return this.engService.getGreeting(name);
    }
}