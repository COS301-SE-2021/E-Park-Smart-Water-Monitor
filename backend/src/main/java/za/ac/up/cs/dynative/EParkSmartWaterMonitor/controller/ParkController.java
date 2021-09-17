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

/**
 * This class will map all the http requests made related to parks to their
 * respected functions in the park service implementation.
 * The http methods that will be used: post, put, get, and delete.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/park")
public class ParkController {

    /**
     * TparkService will be used to call functions in the park service implementation.
     */
    ParkService parkService;

    /**
     * This constructor will set the parkService variable defined previously so that it is not of null value
     * @param parkService will be set and used across the rest of this class.
     */
    @Autowired
    ParkController(@Qualifier("ParkService") ParkService parkService){
        this.parkService = parkService;
    }

    /**
     * This post request will be used to add a park to the system.
     * @param createParkRequest will contain the park name along with its coordinates.
     * @return A CreateParkResponse will be returned with its id, and the success value and status.
     */
    @PostMapping("/addPark")
    public ResponseEntity<Object> addPark(@RequestBody CreateParkRequest createParkRequest) throws InvalidRequestException {
        return new ResponseEntity<>(parkService.createPark(createParkRequest), HttpStatus.OK);
    }

    /**
     * This post request will retrieve all the various water site at a specific park.
     * @param getParkSitesRequest will contain the park id to be used as input.
     * @return A GetParkSitesResponse object will be returned containing the success value, the status and of course a collection of water sites.
     */
    @PostMapping("/getParkWaterSites")
    public ResponseEntity<Object> getParkWaterSites(@RequestBody GetParkSitesRequest getParkSitesRequest) throws InvalidRequestException {
        return new ResponseEntity<>(parkService.getParkWaterSites(getParkSitesRequest),HttpStatus.OK);
    }

    /**
     * This put request will update an existing parks's details.
     * @param request will contain the park id to identify the park, the park name, and its coordinates.
     * @return An EditParkResponse will be returned with its status and success value.
     */
    @PutMapping("/editPark")
    public ResponseEntity<Object> editPark(@RequestBody EditParkRequest request) throws InvalidRequestException {
        return new ResponseEntity<>(parkService.editPark(request),HttpStatus.OK);
    }

    /**
     * This request is for testing purposes only.
     */
    @PostMapping("/deleteInternal")
    public ResponseEntity<Object> deleteInternal(@RequestBody DeleteParkRequest request) {
        return new ResponseEntity<>(parkService.deletePark(request),HttpStatus.OK);
    }

    /**
     * This get request will return all possible parks in the system.
     * @return A GetAllParksResponse will be returned containing all of the parks.
     */
    @GetMapping("/getAllParks")
    public ResponseEntity<Object> getAllParks() {
        return new ResponseEntity<>(parkService.getAllParks(),HttpStatus.OK);
    }

    /**
     * The get request will retrieve all the current parks along with their sites.
     * @return A GetAllParksAndSitesResponse containing a list of parks with their respected water sites.
     */
    @GetMapping("/getAllParksAndSites")
    public ResponseEntity<Object> getAllParksAndSites() {
        return new ResponseEntity<>(parkService.getAllParksAndSites(),HttpStatus.OK);
    }

    /**
     * This delete mapping will be used to delete a specified park.
     * @param request will contain the park id that is to be deleted.
     * @return A DeleteParkResponse object containing the success value and status will be returned.
     */
    @DeleteMapping("/deletePark")
    public ResponseEntity<Object> deletePark(@RequestBody DeleteParkRequest request) {
        return new ResponseEntity<>(parkService.deletePark(request),HttpStatus.OK);
    }
}
