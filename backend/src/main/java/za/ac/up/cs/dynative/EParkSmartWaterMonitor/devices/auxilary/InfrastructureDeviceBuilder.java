package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.auxilary;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.DeviceData;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.sensorConfiguration;

import java.util.ArrayList;

public class InfrastructureDeviceBuilder implements DeviceBuilder  {
    private Device infrastructureDevice;
    private DeviceData deviceData;
    @Override
    public void selectDeviceName(String deviceName)
    {
        infrastructureDevice.setDeviceName(deviceName);
    }

    public InfrastructureDeviceBuilder(Device infrastructureDevice)
    {
        this.infrastructureDevice = infrastructureDevice;
        deviceData= new DeviceData();
    }

    @Override
    public void selectDeviceType()
    {
        infrastructureDevice.setDeviceType("Infrastructure");
    }

    @Override
    public void selectDeviceModel(String deviceModel)
    {
        infrastructureDevice.setDeviceModel(deviceModel);
    }

    @Override
    public void selectDeviceCoordinates(double longitude, double latitude)
    {
        deviceData.setLongitude(longitude);
        deviceData.setLatitude(latitude);
    }

    @Override
    public void selectDeviceConfiguration() {
        ArrayList<sensorConfiguration> deviceConfiguration = new ArrayList<>();
        deviceConfiguration.add(new sensorConfiguration("reportingFrequency",4.0));
        deviceConfiguration.add(new sensorConfiguration("WATER_FLOW_RATE",1.0));
        deviceData.setDeviceConfiguration(deviceConfiguration);
    }
}
