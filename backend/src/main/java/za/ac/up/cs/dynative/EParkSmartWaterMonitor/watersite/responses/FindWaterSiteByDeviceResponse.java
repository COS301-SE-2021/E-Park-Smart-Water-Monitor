package za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

public class FindWaterSiteByDeviceResponse {
    private String status;
    private Boolean success;
    private WaterSite waterSite;

    public FindWaterSiteByDeviceResponse(String status, Boolean success, WaterSite waterSite) {
        this.status = status;
        this.success = success;
        this.waterSite = waterSite;
    }

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
