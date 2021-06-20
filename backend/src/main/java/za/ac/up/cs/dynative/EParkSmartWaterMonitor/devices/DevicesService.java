package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.EditParkRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DevicesService {
    ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request);
    AddWaterSourceDeviceResponse addDevice(AddWaterSourceDeviceRequest addWSDRequest) throws InvalidRequestException;
    Collection<WaterSourceDevice> getAll();
    FindDeviceResponse findDevice(FindDeviceRequest findDeviceRequest) throws InvalidRequestException;
    GetNumDevicesResponse getNumDevices(GetNumDevicesRequest getNumDevicesRequest) throws InvalidRequestException;
    GetParkDevicesResponse getParkDevices(GetParkDevicesRequest getParkDevicesRequest);
    EditDeviceResponse editDevice(EditDeviceRequest editDeviceRequest);
    GetDeviceDataResponse getDeviceData(GetDeviceDataRequest getDeviceDataRequest);
}
