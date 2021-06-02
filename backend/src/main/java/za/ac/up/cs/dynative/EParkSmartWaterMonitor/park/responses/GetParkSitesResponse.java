package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.Collection;

public class GetParkSitesResponse {
    private String status;
    private Boolean success;
    private Collection<WaterSite> site;

    public GetParkSitesResponse(String status, Boolean success, Collection<WaterSite> site) {
        this.status = status;
        this.success = success;
        this.site = site;
    }

    public GetParkSitesResponse() {
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

    public Collection<WaterSite> getSite() {
        return site;
    }

    public void setSite(Collection<WaterSite> site) {
        this.site = site;
    }
}
