package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;


@RestController
@RequestMapping("/api/park")
public class ParkController {

    ParkService parkService;

    @Autowired
    ParkController(@Qualifier("ParkService") ParkService parkService){
        this.parkService = parkService;
    }

    @PostMapping("/addPark")
    public ResponseEntity<Object> addPark(@RequestBody CreateParkRequest createParkRequest) {
        return new ResponseEntity<>(parkService.createPark(createParkRequest), HttpStatus.OK);
    }

    @PostMapping("getParkWaterSites")
    public ResponseEntity<Object> getParkWaterSites(@RequestBody GetParkSitesRequest getParkSitesRequest) {
        return new ResponseEntity<>(parkService.getParkWaterSites(getParkSitesRequest),HttpStatus.OK);
    }
}
