package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

public class SaveSiteRequest {

    private WaterSite site;

    public SaveSiteRequest(WaterSite site) {
        this.site = site;
    }

    public WaterSite getSite() {
        return site;
    }

    public void setSite(WaterSite site) {
        this.site = site;
    }
}
