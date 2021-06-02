package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;


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

    @GetMapping("testBackend")
    public ResponseEntity<Object> testBackend() {
        System.out.println("made it to the controller");
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
