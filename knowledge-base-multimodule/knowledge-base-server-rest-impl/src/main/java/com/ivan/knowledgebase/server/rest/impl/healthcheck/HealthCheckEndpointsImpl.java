package com.ivan.knowledgebase.server.rest.impl.healthcheck;

import javax.ws.rs.ext.Provider;

import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckEndpoints;
import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckResponse;

@Provider
public class HealthCheckEndpointsImpl implements HealthCheckEndpoints {

	@Override
	public HealthCheckResponse getHealthCheckStatus() {
		return new HealthCheckResponse("SUCCESS");
	}

}
