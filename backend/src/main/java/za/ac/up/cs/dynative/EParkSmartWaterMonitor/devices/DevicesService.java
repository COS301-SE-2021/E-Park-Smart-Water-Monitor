package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllDevicesResponse;

import java.util.Collection;

public interface DevicesService {
    ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request);
    AddWaterSourceDeviceResponse addDevice(AddWaterSourceDeviceRequest addWSDRequest);
    Collection<WaterSourceDevice> getAll();
    FindDeviceResponse findDevice(FindDeviceRequest findDeviceRequest);
    GetNumDevicesResponse getNumDevices(GetNumDevicesRequest getNumDevicesRequest);
    GetParkDevicesResponse getParkDevices(GetParkDevicesRequest getParkDevicesRequest);
    EditDeviceResponse editDevice(EditDeviceRequest editDeviceRequest);
    GetDeviceDataResponse getDeviceData(GetDeviceDataRequest getDeviceDataRequest);
    GetAllDevicesResponse getAllDevices();
}
