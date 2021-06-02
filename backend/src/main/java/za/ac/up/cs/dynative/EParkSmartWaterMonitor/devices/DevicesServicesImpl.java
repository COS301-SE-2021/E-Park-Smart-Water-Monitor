package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.SourceDataRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.ReceiveDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.AddWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AttachWaterSourceDeviceResponse;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service("DeviceServiceImpl")
public class DevicesServicesImpl implements DevicesService {

    private DeviceRepo deviceRepo;
    private ParkService parkService;
    private WaterSiteService waterSiteService;
    private SourceDataRepo sourceDataRepo;

    public DevicesServicesImpl(@Qualifier("DeviceRepo") DeviceRepo deviceRepo,@Qualifier("ParkService") ParkService parkService, @Qualifier("WaterSiteServiceImpl") WaterSiteService waterSiteService, @Qualifier("SourceDataRepo") SourceDataRepo sourceDataRepo) {
        this.deviceRepo = deviceRepo;
        this.parkService = parkService;
        this.sourceDataRepo = sourceDataRepo;
        this.waterSiteService=waterSiteService;
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

            AttachWaterSourceDeviceResponse attachWaterSourceDeviceResponse= waterSiteService.attachWaterSourceDevice( new AttachWaterSourceDeviceRequest(addWSDRequest.getSiteId(),newDevice));

            if (!attachWaterSourceDeviceResponse.getSuccess())
            {
                response.setSuccess(false);
                response.setStatus("The water site "+addWSDRequest.getSiteId()+" does not exist.");
            }
            else
            {

                deviceRepo.save(newDevice);
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
            measurement data = new measurement(request.getType(), request.getUnitOfMeasurement(), request.getValue(),request.getDeviceDateTime(), new Date());
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

    @Override
    public GetNumDevicesResponse getNumDevices(GetNumDevicesRequest request) {
        GetNumDevicesResponse getNumDevicesResponse = new GetNumDevicesResponse();
        if (!request.getParkName().equals("")) {
            FindByParkNameResponse parkNameResponse = parkService.findParkByName(new FindByParkNameRequest(request.getParkName()));
            if (parkNameResponse.getPark() != null) {

                getNumDevicesResponse.setNumDevices(deviceRepo.getAllParkDevices(request.getParkName()).size());
                getNumDevicesResponse.setSuccess(true);
            }
        } else getNumDevicesResponse.setSuccess(false);
        return getNumDevicesResponse;
    }
}
