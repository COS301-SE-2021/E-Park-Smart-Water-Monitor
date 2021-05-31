package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.Site;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.SiteService;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    SiteService siteService;


    @Autowired
    SiteController(@Qualifier("SiteServiceImpl") SiteService devicesService){
        this.siteService = devicesService;
    }


    @GetMapping("/getAllSites")
    public java.util.Collection<Site> getDevice() {
        return siteService.getAll();
    }

    @GetMapping("addSite")
    public ResponseEntity<?> addDevice() {
        siteService.addSite();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("getSite")
    public Site getSite() {
      return  siteService.findSite();
    }

}
