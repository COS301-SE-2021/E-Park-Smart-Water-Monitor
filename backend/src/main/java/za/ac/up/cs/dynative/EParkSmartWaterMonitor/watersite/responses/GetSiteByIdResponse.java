package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

/**
 * This class represents the response of the find water site by id request.
 */
public class GetSiteByIdResponse {
    /**
     * Attributes:
     */
    private String status;
    private Boolean success;
    private WaterSite site;

    /**
     * The custom constructor
     * @param status The success status of the request
     * @param success The success value of the request
     * @param site The returned site.
     */
    public GetSiteByIdResponse(String status, Boolean success, WaterSite site) {
        this.status = status;
        this.success = success;
        this.site = site;
    }

    /**
     * Getetrs and setters:
     */
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

    public WaterSite getSite() {
        return site;
    }

    public void setSite(WaterSite site) {
        this.site = site;
    }
}
