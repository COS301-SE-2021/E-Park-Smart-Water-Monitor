package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.InspectionService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.*;

/**
 * This class will map all the http requests made related to the inspections to their
 * respected functions in the inspections service implementation.
 * The http methods that will be used: get, post.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    /**
     * The inspectionService will be used to call functions in the inspection service implementation.
     */
    InspectionService inspectionService;

    /**
     * This constructor will set the inspectionService variable defined previously so that it is not of null value
     * @param inspectionService will be set and used across the rest of this class.
     */
    @Autowired
    InspectionController(@Qualifier("InspectionServiceImpl") InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    /**
     * This post request will add an inspection concerning to a specific device.
     * @param request object containing the device and site id will be passed as input.
     * @return A AddInspectionResponse object containing the inspection id, the success value and status.
     */
    @PostMapping("/addInspection")
    public ResponseEntity<Object> addInspection(@RequestBody AddInspectionRequest request) throws InvalidRequestException {
        return new ResponseEntity<>(inspectionService.addInspection(request), HttpStatus.OK);
    }

    @PostMapping("/getSiteInspections")
    public ResponseEntity<Object> getWaterSiteInspections(@RequestBody GetWaterSiteInspectionsRequest request) {
        return new ResponseEntity<>(inspectionService.getWaterSiteInspections(request), HttpStatus.OK);
    }

    /**
     * This post request will request the inspections related to a specific device.
     * @param request object containing the device id.
     * @return GetDeviceInspectionsResponse object will be returned containing a list of inspections, the success value, and
     * the status.
     */
    @PostMapping("/getDeviceInspections")
    public  ResponseEntity<Object> getDeviceInspections(@RequestBody GetDeviceInspectionsRequest request) {
        return new ResponseEntity<>(inspectionService.getDeviceInspections(request), HttpStatus.OK);
    }

    /**
     * The following post request will set the status of an inspection.
     * @param request will contain the inspection id and the new status.
     * @return A SetInspectionResponse will be sent back. The object will contain the success and status values.
     */
    @PostMapping("/setStatus")
    public ResponseEntity<Object> setInspectionStatus(@RequestBody SetInspectionStatusRequest request) {
        return new ResponseEntity<>(inspectionService.setInspectionStatus(request), HttpStatus.OK);
    }

    /**
     * This post request adds a comment to a specific inspection.
     * @param request contains the inspection id along with the comment to be added.
     * @return SetInspectionCommentResponse object will be returned, and it contains the success value along with the status.
     */
    @PostMapping("/setComments")
    public ResponseEntity<Object> setInspectionComments(@RequestBody SetInspectionCommentsRequest request) {
        return new ResponseEntity<>(inspectionService.setInspectionComments(request), HttpStatus.OK);
    }

    /**
     * This post request will
     * @param request
     * @return
     */
    @PostMapping("/setDescription")
    public ResponseEntity<Object> setInspectionDescription(@RequestBody SetInspectionDescriptionRequest request) {
        return new ResponseEntity<>(inspectionService.setInspectionDescription(request), HttpStatus.OK);
    }

    /**
     * This post request is only used for testing purposes. In the system inspections are not deleted.
     * @param request will contain the inspection id that is to be deleted.
     * @return A DeleteInternalResponse will be returned containing the success value.
     */
    @PostMapping("/deleteInternal")
    public ResponseEntity<Object> deleteInternal(@RequestBody DeleteInternalRequest request) {
        return new ResponseEntity<>(inspectionService.deleteInternal(request), HttpStatus.OK);
    }

    /**
     * This get request will return all inspections in the system.
     * @return The endpoint will return a GetAllInspectionsResponse containing lists of inspections grouped according to their park and also their park id.
     */
    @GetMapping("/getAllInspections")
    public ResponseEntity<Object> getAllInspections() {
        return new ResponseEntity<>(inspectionService.getAllInspections(),HttpStatus.OK);
    }
}
