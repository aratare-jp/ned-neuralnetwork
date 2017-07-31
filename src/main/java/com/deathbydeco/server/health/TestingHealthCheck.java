package com.deathbydeco.server.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by rex on 2017/03/16.
 *
 * This class is used to test out health checking of the server.
 */
public class TestingHealthCheck extends HealthCheck {

    private String template;

    public TestingHealthCheck(String template) {
        this.template = template;
    }

    /**
     * Perform a check of the application component.
     *
     * @return if the component is healthy, a healthy result; otherwise, an
     * unhealthy result with a descriptive error message or exception
     *
     * @throws Exception if there is an unhandled error during the health
     *                   check; this will result in a failed health check
     */
    @Override
    protected Result check() throws Exception {

        final String result = String.format(template, "TEST");

        if (!result.contains("TEST")) {
            return Result.unhealthy("Template doesn't include a name");
        }

        return Result.healthy();
    }
}
