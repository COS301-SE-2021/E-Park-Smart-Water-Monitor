package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;

@Service("AnalyticsServiceImpl")
public class AnalyticsServiceImpl implements AnalyticsService {

    private DevicesService devicesService;

    public AnalyticsServiceImpl(@Qualifier("DeviceServiceImpl") DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    @Override
    public DeviceProjectionResponse deviceProjection(DeviceProjectionRequest deviceProjectionRequest) {
        return null;
    }
}
