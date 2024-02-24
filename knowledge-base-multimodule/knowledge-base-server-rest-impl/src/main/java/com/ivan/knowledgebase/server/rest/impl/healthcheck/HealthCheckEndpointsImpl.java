package com.ivan.knowledgebase.server.rest.impl.healthcheck;



import javax.ws.rs.ext.Provider;

import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckEndpoint;
import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckResponse;

@Provider
public class HealthCheckEndpointsImpl implements HealthCheckEndpoint {

	@Override
	public HealthCheckResponse getHealthCheckStatus() {
		return new HealthCheckResponse("SUCCESS");
	}

	@Override
	public String test() {
		return "Test";
	}

}
