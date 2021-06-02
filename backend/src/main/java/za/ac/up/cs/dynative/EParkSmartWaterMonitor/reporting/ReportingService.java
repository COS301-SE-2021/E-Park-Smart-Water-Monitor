package za.ac.up.cs.dynative.EParkSmartWaterMonitor.reporting;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;

public interface ReportingService {

    GetNumDevicesResponse GetNumDevices(GetNumDevicesRequest request);
}
