package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DeleteDeviceRequest {
    private UUID id;

    public DeleteDeviceRequest(@JsonProperty("id") UUID id)
    {
        this.id = id;
    }

    public UUID getDeviceId()
    {
        return id;
    }

    public void setDeviceRequestId(UUID id)
    {
        this.id = id;
    }
}
