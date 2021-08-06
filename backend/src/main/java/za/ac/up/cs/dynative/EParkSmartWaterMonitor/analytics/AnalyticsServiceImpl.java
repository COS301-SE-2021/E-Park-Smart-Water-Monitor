package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models.WaterLevelProjectionStrategy;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;

@Service("AnalyticsServiceImpl")
public class AnalyticsServiceImpl implements AnalyticsService {

    private DevicesService devicesService;
    private ProjectionStrategyInterface projectionStrategy;

    public AnalyticsServiceImpl(@Qualifier("DeviceServiceImpl") DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    @Override
    public DeviceProjectionResponse deviceProjection(DeviceProjectionRequest request) {

        switch (request.getType()) {
            case "ph":
                projectionStrategy = new PhProjectionStrategy(request);
                return projectionStrategy.predict();
            case "waterlevel":
                projectionStrategy = new WaterLevelProjectionStrategy(request);
                return projectionStrategy.predict();
            case "temperature":
                projectionStrategy = new TemperatureProjectionStrategy(request);
                return projectionStrategy.predict();
        }
        return new DeviceProjectionResponse("Nothing but works", true, request.getType(), request.getLength(), null, null, null);
    }
}
