package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.auxilary;

import java.util.UUID;

public interface DeviceBuilder
{
    void selectDeviceName(String deviceName);
    void selectDeviceType(String deviceType);
    void selectDeviceModel(String deviceModel);
    void selectDeviceCoordinates(double longitude,double latitude);
}
