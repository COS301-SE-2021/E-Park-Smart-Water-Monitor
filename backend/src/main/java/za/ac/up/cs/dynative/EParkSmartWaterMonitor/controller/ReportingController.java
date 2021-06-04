package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.reporting.ReportingService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/reporting")
public class ReportingController {


    private ReportingService reportingService;


    @Autowired
    ReportingController(@Qualifier("ReportingServiceImpl") ReportingService reportingService){
        this.reportingService = reportingService;
    }

}
