package com.ivan.knowledgebase.user.acceptance.test.blackbox;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckEndpoints;
import com.ivan.knowledgebase.server.rest.healthcheck.HealthCheckResponse;
import com.ivan.knowledgebase.user.acceptance.test.Blackbox;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

class HealthCheckEndpointsTest {
    private static final int DEFAULT_TIMEOUT = 3000;

    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckEndpointsTest.class);

    private final HealthCheckEndpoints healthCheckEndpoints = Blackbox.createEndpoint(HealthCheckEndpoints.class)
            .forKnowledgeBaseApi().build();

    private String currentTestName;

    @Test
    void testHealthCheckEndpointShouldResponseWithSuccess() throws Exception {
        LOG.info("1");
        Thread.sleep(DEFAULT_TIMEOUT);
        Assertions.assertThat(true).isEqualTo(false);
        HealthCheckResponse result = healthCheckEndpoints.getHealthCheckStatus();
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
    }

    @Test
    void testHealthCheckEndpointShouldResponseWithSuccess1() throws Exception {
        LOG.info("2");
        Thread.sleep(DEFAULT_TIMEOUT);
        Assertions.assertThat(true).isEqualTo(false);
        HealthCheckResponse result = healthCheckEndpoints.getHealthCheckStatus();
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
    }

    @Test
    void testHealthCheckEndpointShouldResponseWithSuccess2() throws Exception {
        LOG.info("3");
        Thread.sleep(DEFAULT_TIMEOUT);
        Assertions.assertThat(true).isEqualTo(false);
        HealthCheckResponse result = healthCheckEndpoints.getHealthCheckStatus();
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
    }

    @BeforeMethod
    void handleTestMethodName(Method method) {
        this.currentTestName = method.getName();
    }

    @Test
    void testHealthCheckEndpointShouldResponseWithSuccess3(ITestContext context) throws Exception {
        String outputDirectory = context.getOutputDirectory();

        ExtentReports extent = new ExtentReports(
                outputDirectory.replace(' ', '_') + "/extentReport" + Instant.now().toEpochMilli() + ".html", false);
        ExtentTest test1 = extent.startTest(currentTestName);

        test1.log(LogStatus.INFO, "Starting test case");

        LOG.info(currentTestName);
        Thread.sleep(DEFAULT_TIMEOUT);

        test1.log(LogStatus.PASS, "maximize has done");
        Assertions.assertThat(true).isEqualTo(true);

        extent.flush();
    }
}
