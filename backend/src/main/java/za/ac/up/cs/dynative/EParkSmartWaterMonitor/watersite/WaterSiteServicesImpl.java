package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.SaveParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.CanAttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.SaveSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AttachWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.CanAttachWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.SaveSiteResponse;

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

        if (request==null){
            response.setStatus("Request is null");
            response.setSuccess(false);
        }

        if (request.getParkId() != null) {
            WaterSite waterSite = new WaterSite(UUID.randomUUID(),request.getSiteName(),request.getLatitude(), request.getLongitude());

            FindByParkIdResponse findByParkIdResponse = parkService.findByParkId(new FindByParkIdRequest(request.getParkId()));

            if (findByParkIdResponse != null) {
                findByParkIdResponse.getPark().addWaterSite(waterSite);
                waterSiteRepo.save(waterSite);
                parkService.savePark(new SaveParkRequest(findByParkIdResponse.getPark()));
                response.setStatus("Successfully added: " + request.getSiteName());
                response.setSuccess(true);
            }else {
                response.setStatus("Park not found");
                response.setSuccess(false);
            }
        }
        else {
            response.setStatus("No park id specified");
            response.setSuccess(false);
        }

        return response;
    }

    public CanAttachWaterSourceDeviceResponse canAttachWaterSourceDevice(CanAttachWaterSourceDeviceRequest request)  {
        CanAttachWaterSourceDeviceResponse response;
        if (request==null){
            response= new CanAttachWaterSourceDeviceResponse("CRequest is null",false);
            return response;
        }
        if (request.getSiteId()==null){
            response= new CanAttachWaterSourceDeviceResponse("No id specified",false);
            return response;
        }
        Optional<WaterSite> siteToAddTo = waterSiteRepo.findById(request.getSiteId());
        if (siteToAddTo.isPresent())
        {
            response= new CanAttachWaterSourceDeviceResponse("Can attach device to site!",true);
        }
        else
        {
            response = new CanAttachWaterSourceDeviceResponse("Site does not exist", false);
        }

        return response;
    }

    public AttachWaterSourceDeviceResponse attachWaterSourceDevice(AttachWaterSourceDeviceRequest request)  {
        AttachWaterSourceDeviceResponse response;

        if (request.getSiteId()==null){
            response = new AttachWaterSourceDeviceResponse("No id specified", false);
            return response;
        }
        if (request.getDevice()==null){
            response = new AttachWaterSourceDeviceResponse("No device specified", false);
            return response;
        }
        if (request.getDevice().getDeviceId()==null){
            response = new AttachWaterSourceDeviceResponse("No device id specified", false);
            return response;
        }
        Optional<WaterSite> siteToAddTo = waterSiteRepo.findById(request.getSiteId());
        if (siteToAddTo.isPresent()){
            siteToAddTo.get().addWaterSourceDevice(request.getDevice());
            waterSiteRepo.save(siteToAddTo.get());
            response= new AttachWaterSourceDeviceResponse("Successfully attached device to site!",true);
        }else{
            response = new AttachWaterSourceDeviceResponse("Site does not exist", false);
        }
        return response;
    }

    public Collection<WaterSite> getAll() {
        return null;
    }


    @Override
    public GetSiteByIdResponse getSiteById(GetSiteByIdRequest request)  {
        GetSiteByIdResponse response;

        if (request.getSiteId()==null){
            response = new GetSiteByIdResponse("No id specified", false, null);
            return response;
        }
        Optional<WaterSite> foundSite= waterSiteRepo.findById(request.getSiteId());
        if (foundSite.isEmpty()) {
            response = new GetSiteByIdResponse("Site does not exist.", false, null);
        }else{
            response = new GetSiteByIdResponse("Successfully found site.", true, foundSite.get());
        }
        return response;
    }

    @Override
    public SaveSiteResponse saveSite(SaveSiteRequest request) {
        if (request.getSite() != null) {
            waterSiteRepo.save(request.getSite());
            return new SaveSiteResponse("Saved park successfully",true);
        }
        else {
            return new SaveSiteResponse("Error in saving park!",false);
        }
    }

}
