package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public class AddInspectionRequest {

    private UUID deviceId;

    private UUID waterSiteId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Africa/Harare")
    private Date dateDue;

    private String description;

    public AddInspectionRequest(@JsonProperty("deviceId") UUID deviceId,
                                @JsonProperty("siteId") UUID waterSiteId,
                                @JsonProperty("dateDue") Date dateDue,
                                @JsonProperty("description") String description) {
        this.deviceId = deviceId;
        this.waterSiteId = waterSiteId;
        this.dateDue = dateDue;
        this.description = description;
    }

    public UUID getDeviceId() { return deviceId; }

    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }

    public UUID getWaterSiteId() {
        return waterSiteId;
    }

    public void setWaterSiteId(UUID waterSiteId) {
        this.waterSiteId = waterSiteId;
    }

    public Date getDateDue() { return dateDue; }

    public void setDateDue(Date dateDue) { this.dateDue = dateDue; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}