package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.ReceiveDeviceDataResponse;

import java.util.Collection;
import java.util.Optional;

public interface DevicesService {
    public ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request);
    public void addDevice();
    public Collection<WaterSourceDevice> getAll();
    public Optional<WaterSourceDevice> findDevice();
}
