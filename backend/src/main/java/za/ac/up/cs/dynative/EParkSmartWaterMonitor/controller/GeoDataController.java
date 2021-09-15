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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.responses.GetLossDataResponse;

import java.awt.*;
import java.util.ArrayList;

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

    @GetMapping("/getLine")
    public void getLine()
    {
          geoDataService.lineApproximation(geoDataService.convertCoordToGridBlock(new Coordinate(28.28863, -25.88380)),geoDataService.convertCoordToGridBlock(new Coordinate(28.28877, -25.87522)));
    }
    @GetMapping("/getSignalLoss")
    public GeoJSON getSignalLoss()
    {
         return geoDataService.getSignalLoss(28.28863, -25.88380);
    }
}
