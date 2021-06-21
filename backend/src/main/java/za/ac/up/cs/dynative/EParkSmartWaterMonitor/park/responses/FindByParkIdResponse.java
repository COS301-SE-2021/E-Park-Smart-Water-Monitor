package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.Collection;

public class FindByParkIdResponse {

    private Boolean success;
    private Park park;

    public FindByParkIdResponse(Boolean success, Park park) {
        this.success = success;
        this.park = park;
    }

    public FindByParkIdResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Park getPark() {
        return park;
    }

    public void setStatus(Park park) {
        this.park = park;
    }
}
