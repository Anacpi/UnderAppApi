package com.tcc.underapp_api.common.controller;

import com.tcc.underapp_api.common.records.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for application health check endpoints
 */
@RestController
@RequestMapping("health")
public class HealthController {
    
    /**
     * Checks the health of the application
     * 
     * @return the health status and response time
     */
    @GetMapping
    public HealthResponse healthz() {
        long startTime = System.currentTimeMillis();
        return new HealthResponse("Ok", System.currentTimeMillis() - startTime);
    }
}
