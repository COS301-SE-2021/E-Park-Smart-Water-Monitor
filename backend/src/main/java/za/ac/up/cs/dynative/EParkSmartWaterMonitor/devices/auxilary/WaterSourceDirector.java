package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.auxilary;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

public class WaterSourceDirector
{
    private Device waterSourceDevice;
    private DeviceBuilder builder;

    public WaterSourceDirector()
    {
        waterSourceDevice = new Device();
        builder = new WaterSourceDeviceBuilder(waterSourceDevice);
    }

    public Device construct(String deviceName,double longitude,double latitude)
    {
        builder.selectDeviceName(deviceName);
        builder.selectDeviceConfiguration();
        builder.selectDeviceType();
        builder.selectDeviceModel("ESP32");
        builder.selectDeviceCoordinates(longitude,latitude);
        waterSourceDevice = ((WaterSourceDeviceBuilder)builder).getWaterSourceDevice();

        return waterSourceDevice;
    }

}
