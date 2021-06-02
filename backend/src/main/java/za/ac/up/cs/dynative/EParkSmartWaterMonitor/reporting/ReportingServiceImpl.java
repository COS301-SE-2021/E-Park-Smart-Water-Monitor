package za.ac.up.cs.dynative.EParkSmartWaterMonitor.reporting;

import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;

@Service("ReportingServiceImpl")
public class ReportingServiceImpl implements ReportingService {

    public ReportingServiceImpl() {
    }

    public GetNumDevicesResponse GetNumDevices(GetNumDevicesRequest request) {
        return null;
    }


}
