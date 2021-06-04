package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.Collection;

public class GetParkDevicesResponse {

    private String status;
    private Boolean success;
    private Collection<WaterSourceDevice> waterSourceDevices;

    public GetParkDevicesResponse(String status, Boolean success, Collection<WaterSourceDevice> waterSourceDevices) {
        this.status = status;
        this.success = success;
        this.waterSourceDevices = waterSourceDevices;
    }

    public GetParkDevicesResponse() {
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

    public Collection<WaterSourceDevice> getSite() {
        return waterSourceDevices;
    }

    public void setSite(Collection<WaterSourceDevice> waterSourceDevices) {
        this.waterSourceDevices = waterSourceDevices;
    }
}
