package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.DeleteParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.EditParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/park")
public class ParkController {

    ParkService parkService;

    @Autowired
    ParkController(@Qualifier("ParkService") ParkService parkService){
        this.parkService = parkService;
    }

    @PostMapping("/addPark")
    public ResponseEntity<Object> addPark(@RequestBody CreateParkRequest createParkRequest) throws InvalidRequestException {
        return new ResponseEntity<>(parkService.createPark(createParkRequest), HttpStatus.OK);
    }

    @PostMapping("/getParkWaterSites")
    public ResponseEntity<Object> getParkWaterSites(@RequestBody GetParkSitesRequest getParkSitesRequest) throws InvalidRequestException {
        return new ResponseEntity<>(parkService.getParkWaterSites(getParkSitesRequest),HttpStatus.OK);
    }
    @PutMapping("/editPark")
    public ResponseEntity<Object> editPark(@RequestBody EditParkRequest request) throws InvalidRequestException {
        return new ResponseEntity<>(parkService.editPark(request),HttpStatus.OK);
    }

    @PostMapping("/deleteInternal")
    public ResponseEntity<Object> deleteInternal(@RequestBody DeleteParkRequest request) {
        return new ResponseEntity<>(parkService.deletePark(request),HttpStatus.OK);
    }

    @GetMapping("/getAllParks")
    public ResponseEntity<Object> getAllParks() {
        return new ResponseEntity<>(parkService.getAllParks(),HttpStatus.OK);
    }

    @GetMapping("/getAllParksAndSites")
    public ResponseEntity<Object> getAllParksAndSites() {
        return new ResponseEntity<>(parkService.getAllParksAndSites(),HttpStatus.OK);
    }

    @DeleteMapping("/deletePark")
    public ResponseEntity<Object> deletePark(@RequestBody DeleteParkRequest request) {
        return new ResponseEntity<>(parkService.deletePark(request),HttpStatus.OK);
    }
}
