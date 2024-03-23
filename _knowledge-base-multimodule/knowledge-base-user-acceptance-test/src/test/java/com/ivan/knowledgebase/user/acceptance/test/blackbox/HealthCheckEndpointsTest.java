package com.ivan.knowledgebase.user.acceptance.test.blackbox;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckEndpoints;
import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckResponse;
import com.ivan.knowledgebase.user.acceptance.test.Blackbox;

class HealthCheckEndpointsTest {
    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckEndpointsTest.class);

    private final HealthCheckEndpoints healthCheckEndpoints = Blackbox.createEndpoint(HealthCheckEndpoints.class)
            .forKnowledgeBaseApi().build();

    @Test
    void testHealthCheckEndpointShouldResponseWithSuccess() throws Exception {
        LOG.debug("DDDDD");
        LOG.info("IIIIIInfo");
        LOG.warn("WWWWWW");
        LOG.error("EEEEE");

        Assertions.assertThat(true).isEqualTo(false);
        HealthCheckResponse result = healthCheckEndpoints.getHealthCheckStatus();
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
    }

}
