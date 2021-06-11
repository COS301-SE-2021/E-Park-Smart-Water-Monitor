package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.EditParkRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DevicesService {
    ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request);
    AddWaterSourceDeviceResponse addDevice(AddWaterSourceDeviceRequest addWSDRequest);
    Collection<WaterSourceDevice> getAll();
    Optional<WaterSourceDevice> findDevice();
    GetNumDevicesResponse getNumDevices(GetNumDevicesRequest getNumDevicesRequest);
    GetParkDevicesResponse getParkDevices(GetParkDevicesRequest getParkDevicesRequest);
     EditDeviceResponse editDevice(EditDeviceRequest editDeviceRequest);
}
