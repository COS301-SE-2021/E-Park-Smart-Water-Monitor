package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.SaveParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AttachWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service("WaterSiteServiceImpl")
public class WaterSiteServicesImpl implements WaterSiteService
{

    ParkServiceImpl parkService;
    WaterSiteRepo waterSiteRepo;

    @Autowired
    public WaterSiteServicesImpl(@Qualifier("ParkService") ParkServiceImpl parkService, @Qualifier("WaterSiteRepo") WaterSiteRepo sRepo)
    {
        this.waterSiteRepo = sRepo;
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
                waterSiteRepo.save(waterSite);
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

    public AttachWaterSourceDeviceResponse attachWaterSourceDevice(AttachWaterSourceDeviceRequest request)
    {
        Optional<WaterSite> siteToAddTo = waterSiteRepo.findById(request.getSiteId());
        AttachWaterSourceDeviceResponse response;
        if (siteToAddTo.isPresent())
        {
            siteToAddTo.get().addWaterSourceDevice(request.getWaterSourceDevice());
            waterSiteRepo.save(siteToAddTo.get());
            response= new AttachWaterSourceDeviceResponse("Successfully attached device to site!",true);
            waterSiteRepo.save(siteToAddTo.get());
        }
        else
        {
            response = new AttachWaterSourceDeviceResponse("Site does not exist", false);
        }



        return response;
    }

    public Collection<WaterSite> getAll() {
//        return siteRepo.findAll();
        return null;
    }


    @Override
    public GetSiteByIdResponse getSiteById(GetSiteByIdRequest request)
    {
        Optional<WaterSite> foundSite= waterSiteRepo.findById(request.getSiteId());
        GetSiteByIdResponse response;
        if (foundSite.isEmpty())
        {
            response = new GetSiteByIdResponse("Site does not exist.", false, null);
        }
        else
        {
            response = new GetSiteByIdResponse("Successfully found site.", true, foundSite.get());

        }
        return response;
    }

}
