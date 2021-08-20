package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DeviceProjectionRequest {
    private final UUID id;
    private final String type;
    private final int length;

    public DeviceProjectionRequest(@JsonProperty("id")UUID id,
                                   @JsonProperty("type")String type,
                                   @JsonProperty("length")int length) {
        this.id = id;
        this.type = type;
        this.length = length;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }
}
