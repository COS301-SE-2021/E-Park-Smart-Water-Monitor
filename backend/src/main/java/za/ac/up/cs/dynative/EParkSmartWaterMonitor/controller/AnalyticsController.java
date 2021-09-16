package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.AnalyticsService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;

/**
 * This class will map all the http requests made related to the analytics to their
 * appropriate functions in the analytics service implementation.
 * The http methods that will be used: post.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    /**
     * The analyticsService will be used to call functions in the analytics service implementation.
     */
    AnalyticsService analyticsService;


    /**
     * This constructor will set the analyticsService variable defined previously so that it is not of null value
     * @param analyticsService will be set and used across the rest of this class.
     */
    @Autowired
    AnalyticsController(@Qualifier("AnalyticsServiceImpl") AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * This post request will retrieve the device predictions, the graph on frontend makes use of this endpoint to plot its value.
     * @param deviceProjectionRequest contains the device id, the device type and the length.
     * @return A DeviceProjectionResponse will be returned and contain the status and success value; the type and length along with lists of data for the projections.
     */
    @PostMapping("/deviceProjection")
    public ResponseEntity<Object> deviceProjection(@RequestBody DeviceProjectionRequest deviceProjectionRequest) {
        return new ResponseEntity<>(analyticsService.deviceProjection(deviceProjectionRequest), HttpStatus.OK);
    }
}
