package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.SourceData;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.SourceDataRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.ReceiveDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.AddWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.SiteRepo;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service("DeviceServiceImpl")
public class DevicesServicesImpl implements DevicesService {

    private DeviceRepo deviceRepo;

    private SiteRepo siteRepo;
    private SourceDataRepo sourceDataRepo;

    public DevicesServicesImpl(@Qualifier("DeviceRepo") DeviceRepo deviceRepo, @Qualifier("SiteRepo") SiteRepo siteRepo, @Qualifier("SourceDataRepo") SourceDataRepo sourceDataRepo) {
        this.deviceRepo = deviceRepo;
        this.siteRepo = siteRepo;
        this.sourceDataRepo = sourceDataRepo;
    }

    public Collection<WaterSourceDevice> getAll() {
        return deviceRepo.findAll();
    }

    public AddWaterSourceDeviceResponse addDevice(AddWaterSourceDeviceRequest addWSDRequest) {
        WaterSourceDevice newDevice = new WaterSourceDevice(addWSDRequest.getDeviceName(),addWSDRequest.getDeviceModel(),addWSDRequest.getLongitude(),addWSDRequest.getLatitude());
        AddWaterSourceDeviceResponse response = new AddWaterSourceDeviceResponse();

        WaterSourceDevice device = deviceRepo.findWaterSourceDeviceByDeviceName(addWSDRequest.getDeviceName());
        if (device==null)
        {
            //@Qualifier("ParkRepo") ParkRepo parkRepo
            Optional<WaterSite> waterSiteToAddToSite = siteRepo.findById(addWSDRequest.getSiteId());
            if (waterSiteToAddToSite.isEmpty())
            {
                response.setSuccess(false);
                response.setStatus("The water site "+addWSDRequest.getSiteId()+" does not exist.");
            }
            else
            {
                waterSiteToAddToSite.get().addWaterSourceDevice(newDevice);

                deviceRepo.save(newDevice);
                siteRepo.save(waterSiteToAddToSite.get());
                response.setSuccess(true);
                response.setStatus("Device "+addWSDRequest.getDeviceName()+" successfully added");
            }

        }
        else
        {
            response.setSuccess(false);
            response.setStatus("Device "+addWSDRequest.getDeviceName()+" already exists.");
        }

        return response;

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
        if (!device.getDeviceName().equals("")) {
            SourceData data = new SourceData(request.getWaterLevel(),request.getWaterQuality(), request.getWaterTemperature(), request.getDeviceDateTime(), new Date());
            response.setStatus(
                    "Successfully added data send from ESP: "
                            + request.getDeviceName()
                            + "sent at: "
                            + request.getDeviceDateTime());
            response.setSuccess(true);
            device.addDeviceDataProduced(data);
            sourceDataRepo.save(data);
            deviceRepo.save(device);
        }
        else {
            response.setSuccess(false);
            response.setStatus("Request Failed...");
        }
        return response;
    }
}
