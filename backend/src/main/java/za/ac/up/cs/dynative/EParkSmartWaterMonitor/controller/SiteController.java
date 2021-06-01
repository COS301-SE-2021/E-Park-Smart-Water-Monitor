package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.Site;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.SiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    SiteService siteService;

    @Autowired
    SiteController(@Qualifier("SiteServiceImpl") SiteService devicesService){
        this.siteService = devicesService;
    }

    @PostMapping("/addSite")
    public ResponseEntity<Object> addSite(@RequestBody AddSiteRequest addSiteRequest) {
        return new ResponseEntity<>(siteService.addSite(addSiteRequest),HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllSites")
    public java.util.Collection<Site> getDevice() {
        return siteService.getAll();
    }


    @GetMapping("getSite")
    public Site getSite() {
      return  siteService.findSite();
    }

}
