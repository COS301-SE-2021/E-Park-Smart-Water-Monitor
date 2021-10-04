package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.*;
import java.util.Collection;
import java.util.UUID;

public interface WaterSiteService {
    /**
     * This function will add a site to a park
     * @param request of type AddSiteRequest will contain the park id and site information.
     * @return An AddSiteResponse is returned with the status, success value, the water site and the water site id.
     */
    AddSiteResponse addSite(AddSiteRequest request) throws InvalidRequestException;

    /**
     * Getting all the water sites available on the system.
     * @return A collection of water sites will be returned.
     */
    Collection<WaterSite> getAll();

    /**
     * Attach a device to a site.
     * @param request will contain the device to be attached along with the site id.
     * @return the request completion status and success value is returned in the object.
     */
    AttachWaterSourceDeviceResponse attachWaterSourceDevice(AttachWaterSourceDeviceRequest request) throws InvalidRequestException;

    /**
     * Test to determine if it is vallid to attach a water source device.
     * @param request The CanAttachWaterSourceDeviceRequest created in the attachWaterSourceDevice method.
     * @return return a response of the request.
     */
    CanAttachWaterSourceDeviceResponse canAttachWaterSourceDevice(CanAttachWaterSourceDeviceRequest request) throws InvalidRequestException;

    /**
     * This method will get the water site of the id specified or null if it does not exist.
     * @param request The request containing the site id.
     * @return A response containing the site.
     */
    GetSiteByIdResponse getSiteById(GetSiteByIdRequest request) throws InvalidRequestException;

    /**
     * Saving a node in the database.
     * @param request The object containing the details of the water site that is being saved within the database.
     * @return The response of the request indicating the success of the request.
     */
    SaveSiteResponse saveSite(SaveSiteRequest request);

    /**
     * Deleting a water site from the system.
     * @param deleteWaterSiteRequest The request contains the id of the water site to be deleted.
     * @return A response will be returned indicting the success of the request.
     */
    DeleteWaterSiteResponse deleteWaterSite(DeleteWaterSiteRequest deleteWaterSiteRequest);

    /**
     * Edit an existing water site in the system.
     * @param editWaterSiteRequest The water site details to be updated.
     * @return A response containing the success value and status will be returned.
     */
    EditWaterSiteResponse editWaterSite(EditWaterSiteRequest editWaterSiteRequest);

    /**
     * Find a water site by its id.
     * @param findWaterSiteByDeviceRequest the object contains the ID of the water site.
     * @return A response object containing the water site will be returned.
     */
    FindWaterSiteByDeviceResponse findWaterSiteByDeviceId(FindWaterSiteByDeviceRequest findWaterSiteByDeviceRequest);

    /**
     * Retrieve the water site via the devices connected to it.
     * @param id The id of a device connected to the water site.
     * @return A watersite will be returned.
     */
    WaterSite getWaterSiteByRelatedDevice( UUID id);

    /**
     * This method is only used for internal testing purposes.
     */
    DeleteWaterSiteInternalResponse deleteInternal(DeleteWaterSiteInternalRequest request);
}

