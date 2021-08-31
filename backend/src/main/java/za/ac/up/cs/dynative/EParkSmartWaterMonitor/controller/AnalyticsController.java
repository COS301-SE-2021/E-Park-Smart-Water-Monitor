package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.AnalyticsService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    AnalyticsService analyticsService;

    @Autowired
    AnalyticsController(@Qualifier("AnalyticsServiceImpl") AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @PostMapping("/deviceProjection")
    public ResponseEntity<Object> deviceProjection(@RequestBody DeviceProjectionRequest deviceProjectionRequest) {
        return new ResponseEntity<>(analyticsService.deviceProjection(deviceProjectionRequest), HttpStatus.OK);
    }
}
