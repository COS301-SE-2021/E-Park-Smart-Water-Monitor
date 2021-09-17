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
    CanAttachWaterSourceDeviceResponse canAttachWaterSourceDevice(CanAttachWaterSourceDeviceRequest request) throws InvalidRequestException;
    GetSiteByIdResponse getSiteById(GetSiteByIdRequest request) throws InvalidRequestException;
    SaveSiteResponse saveSite(SaveSiteRequest request);
    DeleteWaterSiteResponse deleteWaterSite(DeleteWaterSiteRequest deleteWaterSiteRequest);
    EditWaterSiteResponse editWaterSite(EditWaterSiteRequest editWaterSiteRequest);
    FindWaterSiteByDeviceResponse findWaterSiteByDeviceId(FindWaterSiteByDeviceRequest findWaterSiteByDeviceRequest);
    WaterSite getWaterSiteByRelatedDevice( UUID id);
}

