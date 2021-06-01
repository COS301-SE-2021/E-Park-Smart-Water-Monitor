package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.ReceiveDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.AddWaterSourceDeviceResponse;

import java.util.Collection;
import java.util.Optional;

public interface DevicesService {
    public ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request);
    public AddWaterSourceDeviceResponse addDevice(AddWaterSourceDeviceRequest addWSDRequest);
    public Collection<WaterSourceDevice> getAll();
    public Optional<WaterSourceDevice> findDevice();
}
