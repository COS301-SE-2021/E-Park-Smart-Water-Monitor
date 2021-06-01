package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.SourceData;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.SourceDataRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.ReceiveDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.SiteRepo;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service("DeviceSeriviceImpl")
public class DevicesServicesImpl implements DevicesService {

    DeviceRepo deviceRepo;
    SiteRepo siteRepo;
    SourceDataRepo sourceDataRepo;

    public DevicesServicesImpl(@Qualifier("DeviceRepo") DeviceRepo deviceRepo, @Qualifier("SiteRepo") SiteRepo siteRepo, @Qualifier("SourceDataRepo") SourceDataRepo sourceDataRepo) {
        this.deviceRepo = deviceRepo;
    }

    public Collection<WaterSourceDevice> getAll() {
        return deviceRepo.findAll();
    }

    public void addDevice() {
//        WaterSourceDevice waterSourceDevice = new WaterSourceDevice("Water Device");
//        WaterSourceDevice otherWaterSourceDevice = new WaterSourceDevice("Other Water Device");
//
//        deviceRepo.save(waterSourceDevice);
//        deviceRepo.save(otherWaterSourceDevice);
    }

    public Optional<WaterSourceDevice> findDevice() {

        Optional<WaterSourceDevice> device =  deviceRepo.findById(UUID.randomUUID());
///
        if (device.isPresent())
        {
            device.get().getDeviceName();
        }
        return deviceRepo.findById(UUID.randomUUID());
    }

    @Override
    public ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request) {
        WaterSourceDevice device = deviceRepo.findWaterSourceDeviceByDeviceName(request.getDeviceName());
        ReceiveDeviceDataResponse response = new ReceiveDeviceDataResponse();
        if (device != null) {
            WaterSite site = siteRepo.getWaterSiteByWaterSourceDevicesContains(device);
            SourceData data = new SourceData(request.getWaterLevel(),request.getWaterQuality(), request.getWaterTemperature(), request.getDeviceDateTime(), new Date());
            if (site != null) {
                site.addWaterSiteData(data);
                siteRepo.save(site);
                response.setStatus(
                        "Successfully added data send from ESP: "
                        + request.getDeviceName()
                        + "sent at: "
                        + request.getDeviceDateTime()
                        + " to Site: "
                        + site.getWaterSiteName());
                response.setSuccess(true);
            }
            device.addDeviceDataProduced(data);
            deviceRepo.save(device);
            sourceDataRepo.save(data);
        }
        else {
            response.setSuccess(false);
            response.setStatus("Request Failed...");
        }
        return response;
    }
}
