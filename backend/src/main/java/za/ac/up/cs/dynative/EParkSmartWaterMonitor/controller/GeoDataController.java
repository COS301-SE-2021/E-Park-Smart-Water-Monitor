package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.GeoDataService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.Coordinate;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.GeoJSON;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.responses.GetElevationDataResponse;

import java.awt.*;

@CrossOrigin
@RestController
@RequestMapping("/api/geodata")
public class GeoDataController
{
    GeoDataService geoDataService;
    @Autowired
    GeoDataController(@Qualifier("GeoDataServiceImpl") GeoDataService geoDataServiceService)
    {
        this.geoDataService=geoDataServiceService;
    }


    @GetMapping("/getElevation")
    public GetElevationDataResponse getElevation()
    {
         return geoDataService.getElevationData();
    }
}
