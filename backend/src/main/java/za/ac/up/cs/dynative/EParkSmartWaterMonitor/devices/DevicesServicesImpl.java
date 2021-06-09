package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thingsboard.rest.client.RestClient;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.User;
import org.thingsboard.server.common.data.asset.Asset;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetParkDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetParkDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.ReceiveDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.AddWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AttachWaterSourceDeviceResponse;

import java.util.*;

@Service("DeviceServiceImpl")
public class DevicesServicesImpl implements DevicesService {

//    private String thingsBoardAddress ;
    RestClient thingsBoardClient;
    private DeviceRepo deviceRepo;
    private ParkService parkService;
    private WaterSiteService waterSiteService;
    private MeasurementRepo measurementRepo;

    public DevicesServicesImpl(@Qualifier("DeviceRepo") DeviceRepo deviceRepo,@Qualifier("ParkService") ParkService parkService, @Qualifier("WaterSiteServiceImpl") WaterSiteService waterSiteService, @Qualifier("SourceDataRepo") MeasurementRepo measurementRepo) {
        this.deviceRepo = deviceRepo;
        this.parkService = parkService;
        this.measurementRepo = measurementRepo;
        this.waterSiteService = waterSiteService;
        thingsBoardClient =new RestClient("http://178.62.41.185:8080");
    }

    public Collection<WaterSourceDevice> getAll() {
        return deviceRepo.findAll();
    }

    public AddWaterSourceDeviceResponse addDevice(AddWaterSourceDeviceRequest addWSDRequest)
    {
        WaterSourceDevice newDevice = new WaterSourceDevice(addWSDRequest.getDeviceName(),addWSDRequest.getDeviceModel(),addWSDRequest.getLongitude(),addWSDRequest.getLatitude());
        AddWaterSourceDeviceResponse response = new AddWaterSourceDeviceResponse();

        List<WaterSourceDevice> devices = deviceRepo.findWaterSourceDeviceByDeviceName(addWSDRequest.getDeviceName());
        WaterSourceDevice device = null;

        if (devices == null) {
            device = (WaterSourceDevice) devices.toArray()[0];
        }

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

                thingsBoardClient.login("tenant@thingsboard.org","tenant");

                Asset asset = new Asset();
                asset.setName("TEST");
                asset.setType("dam");
                asset = thingsBoardClient.saveAsset(asset);

                Device deviceToAdd = new Device();
                deviceToAdd.setName(newDevice.getDeviceId().toString());
                deviceToAdd.setType(newDevice.getDeviceModel());
                deviceToAdd = thingsBoardClient.saveDevice(deviceToAdd);
                System.out.println(thingsBoardClient.getDeviceCredentialsByDeviceId(deviceToAdd.getId()));
//                deviceToAdd.
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
        if (device.isPresent())
        {
            device.get().getDeviceName();
        }
        return deviceRepo.findById(UUID.randomUUID());
    }

    @Override
    public ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request) {
        List<WaterSourceDevice> devices = deviceRepo.findWaterSourceDeviceByDeviceName(request.getDeviceName());
        ReceiveDeviceDataResponse response = new ReceiveDeviceDataResponse();

        WaterSourceDevice device = null;
        if (!request.getDeviceName().equals("") && devices.size() > 0) {
            device = devices.get(0);
        }
        if (device != null) {

            Measurement data;
            for (int i = 0; i <request.getMeasurements().size() ; i++)
            {
                data = request.getMeasurements().get(i);
                device.addDeviceDataProduced(data);
                measurementRepo.save(data);
            }
            deviceRepo.save(device);


            response.setStatus(
                    "Successfully added data send from ESP: "
                            + request.getDeviceName()
                            + " sent at: "
                            + request.getMeasurements().get(0).getDeviceDateTime());
            response.setSuccess(true);

        }
        else {
            response.setSuccess(false);
            response.setStatus("Request Failed... fix not appplied!");
        }
        return response;
    }

    @Override
    public GetNumDevicesResponse getNumDevices(GetNumDevicesRequest request) {
        GetNumDevicesResponse getNumDevicesResponse = new GetNumDevicesResponse();
        if (request.getParkId() != null) {
            FindByParkIdResponse findByParkIdResponse = parkService.findByParkId(new FindByParkIdRequest(request.getParkId()));
            if (findByParkIdResponse.getPark() != null) {

                getNumDevicesResponse.setNumDevices(deviceRepo.getAllParkDevices(request.getParkId()).size());
                getNumDevicesResponse.setSuccess(true);
            }
        } else getNumDevicesResponse.setSuccess(false);
        return getNumDevicesResponse;
    }

    @Override
    public GetParkDevicesResponse getParkDevices(GetParkDevicesRequest request) {
        GetParkDevicesResponse getParkDevicesResponse = new GetParkDevicesResponse();
        if (request.getParkId() != null) {

            List<WaterSourceDevice> devices  = deviceRepo.findAll();

            if (devices != null) {
                getParkDevicesResponse.setSite(devices);
                getParkDevicesResponse.setSuccess(true);
                getParkDevicesResponse.setStatus("Successfully got the Park's devices");
            }
        } else {
            getParkDevicesResponse.setStatus("Failed to get the park's devices");
            getParkDevicesResponse.setSuccess(false);
        }
        return getParkDevicesResponse;
    }
}
