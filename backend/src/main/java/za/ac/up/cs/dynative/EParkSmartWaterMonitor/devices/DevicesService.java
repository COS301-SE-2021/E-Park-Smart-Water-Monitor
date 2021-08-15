package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllDevicesResponse;

import java.util.Collection;

public interface DevicesService {
    ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request);
    AddDeviceResponse addDevice(AddDeviceRequest addWSDRequest) throws InvalidRequestException;
    Collection<Device> getAll();
    FindDeviceResponse findDevice(FindDeviceRequest findDeviceRequest);
    GetNumDevicesResponse getNumDevices(GetNumDevicesRequest getNumDevicesRequest) throws InvalidRequestException;
    GetParkDevicesResponse getParkDevices(GetParkDevicesRequest getParkDevicesRequest) throws InvalidRequestException;
    EditDeviceResponse editDevice(EditDeviceRequest editDeviceRequest);
    DeleteDeviceResponse deleteDevice(DeleteDeviceRequest deleteDeviceRequest);
    GetDeviceDataResponse getDeviceData(GetDeviceDataRequest getDeviceDataRequest);
    GetAllDevicesResponse getAllDevices();
    SetMetricFrequencyResponse setMetricFrequency(SetMetricFrequencyRequest setMetricFrequencyRequest);
    PingDeviceResponse pingDevice(PingDeviceRequest pingDeviceRequest);
}
