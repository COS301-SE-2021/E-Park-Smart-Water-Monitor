package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.CanAttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AttachWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.CanAttachWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;

import java.util.Collection;

public interface WaterSiteService {
    AddSiteResponse addSite(AddSiteRequest request);
    Collection<WaterSite> getAll();
    AttachWaterSourceDeviceResponse attachWaterSourceDevice(AttachWaterSourceDeviceRequest request);
    CanAttachWaterSourceDeviceResponse canAttachWaterSourceDevice(CanAttachWaterSourceDeviceRequest request);
    GetSiteByIdResponse getSiteById(GetSiteByIdRequest request);
}

