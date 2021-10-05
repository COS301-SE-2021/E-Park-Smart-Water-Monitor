package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.FindWaterSiteByDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.FindWaterSiteByDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.*;

import java.util.*;

/**
 * This class is the implementation of the Water Site Service Interface.
 */
@Service("WaterSiteServiceImpl")
public class WaterSiteServicesImpl implements WaterSiteService {
    /**
     * Attributes initialized with the @Autowired annotation constructor.
     */
    ParkServiceImpl parkService;
    WaterSiteRepo waterSiteRepo;

    /**
     * Constructor to initialize the attributes
     * @param parkService
     * @param sRepo
     */
    @Autowired
    public WaterSiteServicesImpl(@Qualifier("ParkService") ParkServiceImpl parkService, @Qualifier("WaterSiteRepo") WaterSiteRepo sRepo) {
        this.waterSiteRepo = sRepo;
        this.parkService = parkService;
    }

    /**
     * This method is used to add a water site to park in the system.
     * <p>
     *     A site can only be added if the park id is specified and is valid, and the shape is correct.
     * </p>
     * @param request of type AddSiteRequest will contain the park id and site information.
     * @return An AddSiteResponse is returned, indicating the success status and value of the request,
     * an id of the new site is also in the returned object.
     */
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
                FindByParkIdResponse findByParkIdResponse = parkService.findByParkId(new FindByParkIdRequest(request.getParkId()));

                if (findByParkIdResponse != null) {
                    UUID siteID =UUID.randomUUID();
                    waterSiteRepo.addWatersite(siteID,
                            request.getSiteName(),
                            request.getLatitude(),
                            request.getLongitude(),
                            request.getShape(),
                            request.getLength(),
                            request.getWidth(),
                            request.getRadius(),request.getParkId());
                    response.setStatus("Successfully added: " + request.getSiteName());
                    response.setSuccess(true);
                    response.setId(siteID);
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

    /**
     * This method tests if it is possible to add a water site to a park.
     * @param request The CanAttachWaterSourceDeviceRequest created in the attachWaterSourceDevice method.
     * @return An CanAttachWaterSourceDeviceResponse is returned indicating the success status and value of the request.
     */
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

    /**
     * Method to attach a new device to a site.
     * @param request will contain the device to be attached along with the site id.
     * @return An AttachWaterSourceDeviceResponse will be returned indicating the success of the request.
     */
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

    /**
     * Retrieve a water site based on a specified id.
     * @param request The request containing the site id.
     * @return GetSiteByIdResponse object is returned containing the water site.
     */
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

    /**
     * Saving the water site (new or existing into the database)
     * @param request The object containing the details of the water site that is being saved within the database.
     * @return A SaveWaterSiteResponse is returned indicating the success of the reqyest.
     */
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

    /**
     * Used to delete a water site and its related devices from the system.
     * @param request indicating which water site is to be deleted.
     * @return A DeleteWaterSiteResponse indicating the success of the request is returned.
     */
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

    /**
     * Update an existing water site. The name and coordinates are updatable.
     * @param request a request containing the water site details along with the new details to be updated.
     * @return An EditWaterSiteResponse is returned containing the success value and status of the request.
     */
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

    /**
     * Retrieve a watersite based on a device ID.
     * @param request contains the ID to base the search on
     * @return A watersite along with the success status and value is encapsulated and returned as a FindWaterSiteByDeviceResponse object.
     */
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

    /**
     * Retrieve a water site based on a device connected to it.
     * @param id The id of a device connected to the water site.
     * @return The retrieved Watersite will be returned.
     */
    public WaterSite getWaterSiteByRelatedDevice(UUID id) {
        return  waterSiteRepo.getWaterSiteByRelatedDevice(id);
    }

    /**
     * This method is only used for internal purposes.
     */
    public DeleteWaterSiteInternalResponse deleteInternal(DeleteWaterSiteInternalRequest request){
        waterSiteRepo.deleteById(request.getId());
        return new DeleteWaterSiteInternalResponse(true);
    }


}
