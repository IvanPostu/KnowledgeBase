package com.ivan.knowledgebase.user.acceptance.test.blackbox;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckEndpoints;
import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckResponse;
import com.ivan.knowledgebase.user.acceptance.test.Blackbox;

class HealthCheckEndpointsTest {

    private final HealthCheckEndpoints healthCheckEndpoints = Blackbox.createEndpoint(HealthCheckEndpoints.class)
            .forKnowledgeBaseApi().build();

    @Test
    void testHealthCheckEndpointShouldResponseWithSuccess() throws Exception {
        HealthCheckResponse result = healthCheckEndpoints.getHealthCheckStatus();
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
    }

}
