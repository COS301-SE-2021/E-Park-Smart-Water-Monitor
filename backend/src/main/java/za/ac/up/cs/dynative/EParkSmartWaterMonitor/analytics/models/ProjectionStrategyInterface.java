package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;

public interface ProjectionStrategyInterface {
    DeviceProjectionResponse predict();
}
