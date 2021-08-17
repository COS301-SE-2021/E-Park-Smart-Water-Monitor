package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;

public interface AnalyticsService {
    DeviceProjectionResponse deviceProjection(DeviceProjectionRequest deviceProjectionRequest);
}
