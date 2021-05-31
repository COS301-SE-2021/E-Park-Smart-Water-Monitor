package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;

import java.util.Collection;
import java.util.Optional;

@Service("DeviceSeriviceImpl")
public class DevicesServicesImpl implements DevicesService {

    DeviceRepo deviceRepo;

    public DevicesServicesImpl(@Qualifier("DeviceRepo") DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public Collection<WaterSourceDevice> getAll() {
        return deviceRepo.findAll();
    }

    public void addDevice() {
        WaterSourceDevice waterSourceDevice = new WaterSourceDevice("Water Device");
        WaterSourceDevice otherWaterSourceDevice = new WaterSourceDevice("Other Water Device");

        deviceRepo.save(waterSourceDevice);
        deviceRepo.save(otherWaterSourceDevice);
    }

    public Optional<WaterSourceDevice> findDevice() {

        Optional<WaterSourceDevice> device =  deviceRepo.findById("Water Device");

        if (device.isPresent())
        {
            device.get().getDeviceName();
        }
        return deviceRepo.findById("Water Device");
    }

}
