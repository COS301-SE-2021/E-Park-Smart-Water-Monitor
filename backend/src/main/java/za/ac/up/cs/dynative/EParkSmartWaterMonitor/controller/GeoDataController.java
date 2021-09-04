package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.GeoDataService;

import java.awt.*;

@CrossOrigin
@RestController
@RequestMapping("/api/geodata")
public class GeoDataController
{
    GeoDataService geoDataService;
    @Autowired
    GeoDataController(@Qualifier("GeoDataServiceImpl") GeoDataService geoDataServiceService) {
    }


    @GetMapping("/getParkDevices")
    public void getElevation() {
         geoDataService.lineApproximation(new Point(1,1),new Point(9,9));
    }
}
