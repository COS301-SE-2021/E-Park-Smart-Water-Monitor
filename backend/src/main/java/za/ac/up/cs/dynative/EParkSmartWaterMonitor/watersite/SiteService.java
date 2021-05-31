package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.WaterSourceDevice;

import java.util.Collection;

public interface SiteService {
    public void addSite();
    public Collection<Site> getAll();
    public void addWaterSourceDevice(int SiteID, WaterSourceDevice deviceToAdd);
    public Site findSite();
}
