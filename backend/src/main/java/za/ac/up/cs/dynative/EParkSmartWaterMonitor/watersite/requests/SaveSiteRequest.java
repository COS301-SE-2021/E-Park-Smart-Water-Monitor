package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

/**
 * This class will contain the needed information to save a new or updated water site in the database.
 */
public class SaveSiteRequest {
    /**
     * Attribute:
     */
    private WaterSite site;

    /**
     * The custom constructor
     * @param site the water site to save to the database.
     */
    public SaveSiteRequest(WaterSite site) {
        this.site = site;
    }

    /**
     * getter and setter of the attribute:
     */
    public WaterSite getSite() {
        return site;
    }

    public void setSite(WaterSite site) {
        this.site = site;
    }
}
