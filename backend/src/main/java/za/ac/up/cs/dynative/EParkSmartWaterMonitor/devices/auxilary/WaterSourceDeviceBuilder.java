package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.auxilary;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.DeviceData;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.sensorConfiguration;

import java.util.ArrayList;

public class WaterSourceDeviceBuilder implements DeviceBuilder {
    private Device waterSourceDevice;
    private DeviceData deviceData;
    @Override
    public void selectDeviceName(String deviceName)
    {
        waterSourceDevice.setDeviceName(deviceName);
    }

    public WaterSourceDeviceBuilder(Device waterSourceDevice) {
        this.waterSourceDevice = waterSourceDevice;
        deviceData= new DeviceData();
    }

    @Override
    public void selectDeviceType()
    {
        waterSourceDevice.setDeviceType("WaterSource");
    }

    @Override
    public void selectDeviceModel(String deviceModel)
    {
        waterSourceDevice.setDeviceModel(deviceModel);
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
        deviceConfiguration.add(new sensorConfiguration("WATER_TEMP",1.0));
        deviceConfiguration.add(new sensorConfiguration("WATER_LEVEL",1.0));
        deviceConfiguration.add(new sensorConfiguration("WATER_QUALITY",1.0));
        deviceData.setDeviceConfiguration(deviceConfiguration);
    }

    public Device getWaterSourceDevice() {
        return waterSourceDevice;
    }

}
