package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

/**
 * This class represents the response of the find water site request.
 */
public class FindWaterSiteByDeviceResponse {
    /**
     * Attributes:
     */
    private String status;
    private Boolean success;
    private WaterSite waterSite;

    /**
     * Custom constructor:
     * @param status The success status of the request
     * @param success The success value of the request
     * @param waterSite The water site that is found or null
     */
    public FindWaterSiteByDeviceResponse(String status, Boolean success, WaterSite waterSite) {
        this.status = status;
        this.success = success;
        this.waterSite = waterSite;
    }

    /**
     * Getters and setters:
     */
    public WaterSite getWaterSite() {
        return waterSite;
    }

    public void setWaterSite(WaterSite waterSite) {
        this.waterSite = waterSite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
