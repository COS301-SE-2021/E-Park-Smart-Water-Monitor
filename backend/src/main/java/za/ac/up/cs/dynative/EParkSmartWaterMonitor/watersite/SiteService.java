package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;

import java.util.Collection;

public interface SiteService {
    public AddSiteResponse addSite(AddSiteRequest request);
    public Collection<Site> getAll();
    public void addWaterSourceDevice(int SiteID, WaterSourceDevice deviceToAdd);
    public Site findSite();
}
