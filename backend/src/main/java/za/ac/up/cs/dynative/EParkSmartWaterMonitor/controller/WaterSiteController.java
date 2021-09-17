package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.DeleteWaterSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.EditWaterSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;

/**
 * This class will map all the http requests made related to water sites to their
 * respected functions in the water site service implementation.
 * The http methods that will be used: post, put, get, and delete.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/sites")
public class WaterSiteController {

    /**
     * The waterSiteService will be used to call functions in the water site service implementation.
     */
    WaterSiteService waterSiteService;

    /**
     * This constructor will set the waterSiteService variable defined previously so that it is not of null value
     * @param devicesService will be set and used across the rest of this class.
     */
    @Autowired
    WaterSiteController(@Qualifier("WaterSiteServiceImpl") WaterSiteService devicesService){
        this.waterSiteService = devicesService;
    }

    /**
     * THis post request will be used to create a new site to an existing park.
     * @param addSiteRequest object will contain the water site details (park id, site name, coordinates, the shape and length along with the radius and width.)
     * @return An AddWaterSiteResponse object will be sent back containing the status and success value of the request, the site details and the site's id.
     */
    @PostMapping("/addSite")
    public ResponseEntity<Object> addSite(@RequestBody AddSiteRequest addSiteRequest) throws InvalidRequestException {
        return new ResponseEntity<>(waterSiteService.addSite(addSiteRequest),HttpStatus.ACCEPTED);
    }

    /**
     * This is post request is only used for testing purposes.
     */
    @PostMapping("/deleteInternal")
    public ResponseEntity<Object> deleteInternal(@RequestBody DeleteWaterSiteRequest deleteWaterSiteRequest) {
        return new ResponseEntity<>(waterSiteService.deleteWaterSite(deleteWaterSiteRequest), HttpStatus.OK);
    }

    /**
     * This get request will return all the water sites located within the system.
     * @return a collection of water sites will be returned.
     */
    @GetMapping("/getAllSites")
    public java.util.Collection<WaterSite> getDevice() {
        return waterSiteService.getAll();
    }

    /**
     * This post request will be used to retrieve a specific water site.
     * @param request contains the site id that will be used as input.
     * @return A GetSiteByIdResponse will be returned to the caller with the specific site and a status and success value.
     */
    @PostMapping("getSite")
    public ResponseEntity<Object> getSite(@RequestBody GetSiteByIdRequest request) throws InvalidRequestException {
      return  new ResponseEntity<>(waterSiteService.getSiteById(request),HttpStatus.ACCEPTED);
    }

    /**
     * This delete request will be used to remove a specified water site from the system.
     * @param deleteWaterSiteRequest contains the id of the water site to be removed.
     * @return A DeleteWaterSiteResponse will be returned and contains the success value and status.
     */
    @DeleteMapping("/deleteWaterSite")
    public ResponseEntity<Object> deleteWaterSite(@RequestBody DeleteWaterSiteRequest deleteWaterSiteRequest) {
        return new ResponseEntity<>(waterSiteService.deleteWaterSite(deleteWaterSiteRequest), HttpStatus.OK);
    }

    /**
     * This put endpoint will be used to update an existing water site's data.
     * @param editWaterSiteRequest contains the detail to be changes. The following can be changes: the site name and coordinates.
     * @return a EditWaterSiteResponse object containing the status and success value.
     */
    @PutMapping("/editWaterSite")
    public ResponseEntity<Object> editWaterSite(@RequestBody EditWaterSiteRequest editWaterSiteRequest) {
        return new ResponseEntity<>(waterSiteService.editWaterSite(editWaterSiteRequest), HttpStatus.OK);
    }
}
