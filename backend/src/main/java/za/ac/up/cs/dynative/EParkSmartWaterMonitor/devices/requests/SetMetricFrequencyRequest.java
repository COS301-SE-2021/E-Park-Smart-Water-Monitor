package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SetMetricFrequencyRequest {
    private UUID id;
    private double value;

    public SetMetricFrequencyRequest(@JsonProperty("id") UUID id,
                                @JsonProperty("value") double value)
    {
        this.id = id;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
