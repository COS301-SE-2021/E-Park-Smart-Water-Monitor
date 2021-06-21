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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;

@CrossOrigin
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

    @GetMapping("/getAllSites")
    public java.util.Collection<WaterSite> getDevice() {
        return waterSiteService.getAll();
    }

    @PostMapping("getSite")
    public ResponseEntity<Object> getSite(@RequestBody GetSiteByIdRequest request) throws InvalidRequestException {

      return  new ResponseEntity<>(waterSiteService.getSiteById(request),HttpStatus.ACCEPTED);
    }

}
