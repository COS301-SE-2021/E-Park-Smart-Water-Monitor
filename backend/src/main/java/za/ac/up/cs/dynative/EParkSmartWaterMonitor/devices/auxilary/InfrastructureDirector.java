package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.auxilary;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

public class InfrastructureDirector
{
    private Device infrastructureDevice;
    private DeviceBuilder builder;

    public InfrastructureDirector()
    {
        infrastructureDevice = new Device();
        builder = new InfrastructureDeviceBuilder(infrastructureDevice);
    }

    public Device construct(String deviceName,double longitude,double latitude)
    {
        builder.selectDeviceName(deviceName);
        builder.selectDeviceConfiguration();
        builder.selectDeviceType();
        builder.selectDeviceModel("ESP32");
        builder.selectDeviceCoordinates(longitude,latitude);
        infrastructureDevice = ((InfrastructureDeviceBuilder)builder).getInfrastructureDevice();

        return infrastructureDevice;
    }


}
