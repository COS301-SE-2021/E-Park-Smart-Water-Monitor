package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.SaveParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.SiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;

import java.util.Collection;
import java.util.UUID;

@Service("SiteServiceImpl")
public class SiteServicesImpl implements SiteService
{

    ParkServiceImpl parkService;
    SiteRepo siteRepo;

    @Autowired
    public SiteServicesImpl(@Qualifier("ParkService") ParkServiceImpl parkService, @Qualifier("SiteRepo") SiteRepo sRepo)
    {
        this.siteRepo = sRepo;
        this.parkService = parkService;
    }

    @Override
    public AddSiteResponse addSite(AddSiteRequest request) {
        AddSiteResponse response = new AddSiteResponse();

        if (!request.getParkName().equals("")) {
            WaterSite waterSite = new WaterSite(UUID.randomUUID(),request.getSiteName(),request.getLatitude(), request.getLongitude());

            FindByParkNameResponse findByParkNameResponse = parkService.findParkByName(new FindByParkNameRequest(request.getParkName()));

            if (findByParkNameResponse != null) {
                findByParkNameResponse.getPark().addWaterSite(waterSite);
                siteRepo.save(waterSite);
                parkService.savePark(new SaveParkRequest(findByParkNameResponse.getPark()));
                response.setStatus("Successfully added: " + request.getSiteName());
                response.setSuccess(true);
            }
        }
        else {
            response.setStatus("No Park Name specified! No park to add site to!");
            response.setSuccess(false);
        }

        return response;
    }

    public void addWaterSourceDevice(int siteID, WaterSourceDevice deviceToAdd)
    {
//        Optional<Site> siteToAddTo =findSite(siteID);
//        if (siteToAddTo.isPresent())
//        {
//            Site awe = siteToAddTo;
//        }
//        siteToAddTo.

    }

    public Collection<Site> getAll() {
//        return siteRepo.findAll();
        return null;
    }


    @Override
    public Site findSite() {
//        System.out.println("FOUND");
//        Long l = new Long(2);
//        return siteRepo.findBySiteName("Lake A");
        return null;
    }

}
