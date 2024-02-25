package com.ivan.knowledgebase.server.rest.healthcheck;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/healthcheck")
public interface HealthCheckEndpoints {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    HealthCheckResponse getHealthCheckStatus();

}
