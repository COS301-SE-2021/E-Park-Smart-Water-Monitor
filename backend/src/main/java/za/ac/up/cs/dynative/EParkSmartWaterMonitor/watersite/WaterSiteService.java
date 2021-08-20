package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import org.springframework.data.repository.query.Param;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.*;

import java.util.Collection;
import java.util.UUID;

public interface WaterSiteService {

    AddSiteResponse addSite(AddSiteRequest request) throws InvalidRequestException;
    Collection<WaterSite> getAll();
    AttachWaterSourceDeviceResponse attachWaterSourceDevice(AttachWaterSourceDeviceRequest request) throws InvalidRequestException;
    CanAttachWaterSourceDeviceResponse canAttachWaterSourceDevice(CanAttachWaterSourceDeviceRequest request) throws InvalidRequestException;
    GetSiteByIdResponse getSiteById(GetSiteByIdRequest request) throws InvalidRequestException;
    SaveSiteResponse saveSite(SaveSiteRequest request);
    DeleteWaterSiteResponse deleteWaterSite(DeleteWaterSiteRequest deleteWaterSiteRequest);
    EditWaterSiteResponse editWaterSite(EditWaterSiteRequest editWaterSiteRequest);
    FindWaterSiteByDeviceResponse findWaterSiteByDeviceId(FindWaterSiteByDeviceRequest findWaterSiteByDeviceRequest);
    WaterSite getWaterSiteByRelatedDevice( UUID id);
}

