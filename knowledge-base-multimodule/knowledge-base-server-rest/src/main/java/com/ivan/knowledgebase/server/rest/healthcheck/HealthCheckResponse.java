package com.ivan.knowledgebase.server.rest.healthcheck;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class HealthCheckResponse {
	private final String status;

	@JsonCreator
	public HealthCheckResponse(String status) {
		this.status = status;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

}
