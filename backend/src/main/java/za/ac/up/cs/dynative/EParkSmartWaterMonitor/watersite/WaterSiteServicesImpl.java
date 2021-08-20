package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.FindWaterSiteByDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.FindWaterSiteByDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.SaveParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.*;

import java.util.*;

@Service("WaterSiteServiceImpl")
public class WaterSiteServicesImpl implements WaterSiteService {
    ParkServiceImpl parkService;
    WaterSiteRepo waterSiteRepo;

    @Autowired
    public WaterSiteServicesImpl(@Qualifier("ParkService") ParkServiceImpl parkService, @Qualifier("WaterSiteRepo") WaterSiteRepo sRepo) {
        this.waterSiteRepo = sRepo;
        this.parkService = parkService;
    }

    @Override
    public AddSiteResponse addSite(AddSiteRequest request) {
        AddSiteResponse response = new AddSiteResponse();
        if (request==null){
            response.setStatus("Request is null");
            response.setSuccess(false);
            return response;
        }
        if (request.getParkId() != null) {
            if (Objects.equals(request.getShape(), "circle") || Objects.equals(request.getShape(), "rectangle")
                    && (request.getLength() >= 0 && request.getWidth() >= 0 && request.getRadius() >= 0)) {
                WaterSite waterSite = new WaterSite(UUID.randomUUID(),
                        request.getSiteName(),
                        request.getLatitude(),
                        request.getLongitude(),
                        request.getShape(),
                        request.getLength(),
                        request.getWidth(),
                        request.getRadius());

                FindByParkIdResponse findByParkIdResponse = parkService.findByParkId(new FindByParkIdRequest(request.getParkId()));

                if (findByParkIdResponse != null) {
                    findByParkIdResponse.getPark().addWaterSite(waterSite);
                    waterSiteRepo.save(waterSite);
                    parkService.savePark(new SaveParkRequest(findByParkIdResponse.getPark()));
                    response.setStatus("Successfully added: " + request.getSiteName());
                    response.setSuccess(true);
                    response.setId(waterSite.getId());
                }else {
                    response.setStatus("Park not found");
                    response.setSuccess(false);
                }
            }
            else {
                response.setStatus("Invalid watersite shape");
                response.setSuccess(false);
            }
        } else {
            response.setStatus("No park id specified");
            response.setSuccess(false);
        }
        return response;
    }

    public CanAttachWaterSourceDeviceResponse canAttachWaterSourceDevice(CanAttachWaterSourceDeviceRequest request)  {
        CanAttachWaterSourceDeviceResponse response;
        if (request==null){
            response= new CanAttachWaterSourceDeviceResponse("Request is null",false);
            return response;
        }
        if (request.getSiteId()==null){
            response= new CanAttachWaterSourceDeviceResponse("No id specified",false);
            return response;
        }
        Optional<WaterSite> siteToAddTo = waterSiteRepo.findById(request.getSiteId());
        if (siteToAddTo.isPresent()) {
            response= new CanAttachWaterSourceDeviceResponse("Can attach device to site!",true);
        } else {
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

    @Override
    public DeleteWaterSiteResponse deleteWaterSite(DeleteWaterSiteRequest request) {
        System.out.println(request.getWaterSiteId());
        if (request.getWaterSiteId() == null) {
            return new DeleteWaterSiteResponse("No watersite id specified.", false);
        }
        Optional<WaterSite> waterSite = waterSiteRepo.findById(request.getWaterSiteId());
        if (waterSite.isPresent()) {
            waterSiteRepo.deletEntireWaterSite(waterSite.get().getId());
            return new DeleteWaterSiteResponse("Successfully deleted the watersite and all related entities.", true);
        }
        return new DeleteWaterSiteResponse("No watersite with this id exists.", false);
    }

    @Override
    public EditWaterSiteResponse editWaterSite(EditWaterSiteRequest request) {
        EditWaterSiteResponse response = new EditWaterSiteResponse();
        if (request.getId() != null) {
            Optional<WaterSite> waterSiteToEdit = waterSiteRepo.findById(request.getId());
            if (waterSiteToEdit.isPresent()) {
                if (!request.getSiteName().equals("")) {
                    waterSiteToEdit.get().setWaterSiteName(request.getSiteName());
                }
                if (request.getLatitude() != 0) {
                    waterSiteToEdit.get().setLatitude(request.getLatitude());
                }
                if (request.getLongitude() != 0) {
                    waterSiteToEdit.get().setLongitude(request.getLongitude());
                }
                response.setStatus("Watersite successfully edited.");
                response.setSuccess(true);
                waterSiteRepo.save(waterSiteToEdit.get());
            }
            else {
                response.setStatus("Watersite does not exist.");
                response.setSuccess(false);
            }
        }
        else {
            response.setStatus("No watersite id specified");
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public FindWaterSiteByDeviceResponse findWaterSiteByDeviceId(FindWaterSiteByDeviceRequest request) {
        if (request == null) {
            return new FindWaterSiteByDeviceResponse("Request is null",false,null);
        }
        if (request.getDeviceID() == null) {
            return new FindWaterSiteByDeviceResponse("No device ID specified",false,null);
        }
        Optional<WaterSite> waterSite = waterSiteRepo.findWaterSiteByDeviceId(request.getDeviceID());
        if (waterSite.isPresent()) {
            return new FindWaterSiteByDeviceResponse("Watersite found",true,waterSite.get());
        }
        else
            return new FindWaterSiteByDeviceResponse("Watersite not found",false,null);
    }
    public WaterSite getWaterSiteByRelatedDevice(UUID id)
    {
        return  waterSiteRepo.getWaterSiteByRelatedDevice(id);
    }

}
