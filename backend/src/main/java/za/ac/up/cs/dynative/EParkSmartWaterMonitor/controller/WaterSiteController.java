package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.Site;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.DeleteWaterSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.EditWaterSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/sites")
public class WaterSiteController {

    WaterSiteService waterSiteService;

    @Autowired
    WaterSiteController(@Qualifier("WaterSiteServiceImpl") WaterSiteService devicesService){
        this.waterSiteService = devicesService;
    }

    @PostMapping("/addSite")
    public ResponseEntity<Object> addSite(@RequestBody AddSiteRequest addSiteRequest) throws InvalidRequestException {
        return new ResponseEntity<>(waterSiteService.addSite(addSiteRequest),HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteInternal")
    public ResponseEntity<Object> deleteInternal(@RequestBody DeleteWaterSiteRequest deleteWaterSiteRequest) {
        return new ResponseEntity<>(waterSiteService.deleteWaterSite(deleteWaterSiteRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllSites")
    public java.util.Collection<WaterSite> getDevice() {
        return waterSiteService.getAll();
    }

    @PostMapping("getSite")
    public ResponseEntity<Object> getSite(@RequestBody GetSiteByIdRequest request) throws InvalidRequestException {
      return  new ResponseEntity<>(waterSiteService.getSiteById(request),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteWaterSite")
    public ResponseEntity<Object> deleteWaterSite(@RequestBody DeleteWaterSiteRequest deleteWaterSiteRequest) {
        return new ResponseEntity<>(waterSiteService.deleteWaterSite(deleteWaterSiteRequest), HttpStatus.OK);
    }

    @PutMapping("/editWaterSite")
    public ResponseEntity<Object> editWaterSite(@RequestBody EditWaterSiteRequest editWaterSiteRequest) {
        return new ResponseEntity<>(waterSiteService.editWaterSite(editWaterSiteRequest), HttpStatus.OK);
    }
}
