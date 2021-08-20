package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.UUID;

public class AddSiteResponse {
    private String status;
    private Boolean success;
    private WaterSite site;
    private UUID id;

    public AddSiteResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public AddSiteResponse() {
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

    public WaterSite getSite() {
        return site;
    }

    public void setSite(WaterSite site) {
        this.site = site;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
