package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public class AddInspectionRequest {

    private UUID deviceId;
    private Date dateDue;
    private String description;

    public AddInspectionRequest(@JsonProperty("deviceId") UUID deviceId,
                                @JsonProperty("dateDue") Date dateDue,
                                @JsonProperty("description") String description) {
        this.deviceId = deviceId;
        this.dateDue = dateDue;
        this.description = description;
    }

    public UUID getDeviceId() { return deviceId; }

    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }

    public Date getDateDue() { return dateDue; }

    public void setDateDue(Date dateDue) { this.dateDue = dateDue; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}