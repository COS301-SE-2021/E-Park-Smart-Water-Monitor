package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iot.IotClient;
import software.amazon.awssdk.services.iot.model.AttributePayload;
import software.amazon.awssdk.services.iot.model.CreateThingRequest;
import software.amazon.awssdk.services.iot.model.CreateThingResponse;
import software.amazon.awssdk.services.iotdataplane.IotDataPlaneClient;
import software.amazon.awssdk.services.iotdataplane.model.UpdateThingShadowRequest;
import software.amazon.awssdk.services.iotdataplane.model.UpdateThingShadowResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.sensorConfiguration;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.CanAttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AttachWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.CanAttachWaterSourceDeviceResponse;

import java.util.*;

@Service("DeviceServiceImpl")
public class DevicesServicesImpl implements DevicesService {

    private DeviceRepo deviceRepo;
    private ParkService parkService;
    private WaterSiteService waterSiteService;
    private MeasurementRepo measurementRepo;
    private IotClient iotClient;
    private IotDataPlaneClient iotDataPlaneClient;
    private AmazonDynamoDB dynamoDBClient;
    private DynamoDB dynamoDB;

    public DevicesServicesImpl(@Qualifier("WaterSourceDeviceRepo") DeviceRepo deviceRepo,
                               @Qualifier("ParkService") ParkService parkService,
                               @Qualifier("WaterSiteServiceImpl") WaterSiteService waterSiteService,
                               @Qualifier("SourceDataRepo") MeasurementRepo measurementRepo) {
        this.deviceRepo = deviceRepo;
        this.parkService = parkService;
        this.measurementRepo = measurementRepo;
        this.waterSiteService = waterSiteService;
        this.iotClient = IotClient.builder().region(Region.US_EAST_2).build();
        this.iotDataPlaneClient = IotDataPlaneClient.builder().region(Region.US_EAST_2).build();
        this.dynamoDBClient = AmazonDynamoDBClient.builder().withRegion(String.valueOf(Region.US_EAST_2)).build();
        this.dynamoDB = new DynamoDB(dynamoDBClient);
    }

    public Collection<Device> getAll() {
        return deviceRepo.findAll();
    }

    public AddDeviceResponse addDevice(AddDeviceRequest addDeviceRequest) throws InvalidRequestException {
        AddDeviceResponse response = new AddDeviceResponse();
        if (addDeviceRequest.getParkName().equals("")||addDeviceRequest.getSiteId()==null||addDeviceRequest.getDeviceModel().equals("")||addDeviceRequest.getDeviceType().equals("")||addDeviceRequest.getDeviceName().equals("")) {
            response.setSuccess(false);
            response.setStatus("Request is missing parameters.");
            return response;
        }
        List<Device> devices = deviceRepo.findDeviceByDeviceName(addDeviceRequest.getDeviceName());
        if (devices.size() == 0) {
            CanAttachWaterSourceDeviceResponse canAttachWaterSourceDeviceResponse =
                    waterSiteService.canAttachWaterSourceDevice(new CanAttachWaterSourceDeviceRequest(addDeviceRequest.getSiteId()));
            if (!canAttachWaterSourceDeviceResponse.getSuccess()) {
                response.setSuccess(false);
                response.setStatus("The water site " + addDeviceRequest.getSiteId() + " does not exist.");
                return response;
            } else {
                Map<String, String> attributes = Map.of("deviceModel",addDeviceRequest.getDeviceModel());
                AttributePayload attributePayload = AttributePayload.builder()
                        .attributes(attributes)
                        .build();
                CreateThingRequest createThingRequest = CreateThingRequest.builder()
                        .thingName(addDeviceRequest.getDeviceName())
                        .thingTypeName("WaterSourceDevice")
                        .attributePayload(attributePayload)
                        .build();
                CreateThingResponse createThingResponse = iotClient.createThing(createThingRequest);
                Device newDevice = new Device(UUID.fromString(createThingResponse.thingId()),addDeviceRequest.getDeviceName(),addDeviceRequest.getDeviceType(), addDeviceRequest.getDeviceModel(), addDeviceRequest.getLongitude(), addDeviceRequest.getLatitude());
                AttachWaterSourceDeviceResponse attachWaterSourceDeviceResponse =
                        waterSiteService.attachWaterSourceDevice(new AttachWaterSourceDeviceRequest(addDeviceRequest.getSiteId(), newDevice));
                String payload = "{\"state\": {\"reported\": {";
                payload += newDevice.getDeviceData().toString();
                payload += "}}}";
                SdkBytes shadowPayload = SdkBytes.fromUtf8String(payload);
                UpdateThingShadowRequest updateThingShadowRequest = UpdateThingShadowRequest.builder()
                        .thingName(createThingResponse.thingName())
                        .shadowName(createThingResponse.thingName()+"_Shadow")
                        .payload(shadowPayload)
                        .build();
                UpdateThingShadowResponse updateThingShadowResponse = iotDataPlaneClient.updateThingShadow(updateThingShadowRequest);
                deviceRepo.save(newDevice);
                response.setSuccess(true);
                response.setStatus("Device " + addDeviceRequest.getDeviceName() + " successfully added");
            }
        } else {
            response.setSuccess(false);
            response.setStatus("Device " + addDeviceRequest.getDeviceName() + " already exists.");
        }
        return response;
    }

    public FindDeviceResponse findDevice(FindDeviceRequest findDeviceRequest)  {
        if (findDeviceRequest==null){
            return new FindDeviceResponse("Request is null",false,null);
        }
        if (findDeviceRequest.getDeviceID()==null){
            return new FindDeviceResponse("No device ID specified",false,null);
        }
        Optional<Device> device = deviceRepo.findById(findDeviceRequest.getDeviceID());
        if (device.isPresent()) {
            return new FindDeviceResponse("Device found",true,device.get());
        }
        else
            return new FindDeviceResponse("Device not found",false,null);

    }

    @Override
    public ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request) {
        if (request.getDeviceName().equals("")){
            ReceiveDeviceDataResponse response= new ReceiveDeviceDataResponse();
            response.setSuccess(false);
            response.setStatus("No device name is specified.");
            return response;
        }
        List<Device> devices = deviceRepo.findDeviceByDeviceName(request.getDeviceName());
        ReceiveDeviceDataResponse response = new ReceiveDeviceDataResponse();
        if (devices.size()==0){
            response.setSuccess(false);
            response.setStatus("Device with that name does not exist");
            return response;
        }
        Device device = null;
        if (!request.getDeviceName().equals("") && devices.size() > 0) {
            device = devices.get(0);
        }
        if (device != null) {
            Measurement data;
            for (int i = 0; i < request.getMeasurements().size(); i++) {
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
        } else {
            response.setSuccess(false);
            response.setStatus("Request Failed... fix not applied!");
        }
        return response;
    }

    @Override
    public GetNumDevicesResponse getNumDevices(GetNumDevicesRequest request) throws InvalidRequestException {
        GetNumDevicesResponse getNumDevicesResponse = new GetNumDevicesResponse();
        if (request==null){
            getNumDevicesResponse.setNumDevices(-1);
            getNumDevicesResponse.setSuccess(false);
            return getNumDevicesResponse;
        }
        if (request.getParkId() != null) {
            FindByParkIdResponse findByParkIdResponse = parkService.findByParkId(new FindByParkIdRequest(request.getParkId()));
            if (findByParkIdResponse.getPark() != null) {

                getNumDevicesResponse.setNumDevices(deviceRepo.getAllParkDevices(request.getParkId()).size());
                getNumDevicesResponse.setSuccess(true);
            }else{
                getNumDevicesResponse.setNumDevices(-2);
                getNumDevicesResponse.setSuccess(false);
                return getNumDevicesResponse;
            }
        } else{
            getNumDevicesResponse.setNumDevices(-3);
            getNumDevicesResponse.setSuccess(false);
            return getNumDevicesResponse;
        }
        return getNumDevicesResponse;
    }

    @Override
    public GetParkDevicesResponse getParkDevices(GetParkDevicesRequest request)  {
        GetParkDevicesResponse getParkDevicesResponse = new GetParkDevicesResponse();
        if (request==null){
            getParkDevicesResponse.setSite(null);
            getParkDevicesResponse.setSuccess(false);
            getParkDevicesResponse.setStatus("Request is null");
            return getParkDevicesResponse;
        }
        if (request.getParkId() != null) {
            List<Device> devices = deviceRepo.findAll();
            if (devices != null) {
                getParkDevicesResponse.setSite(devices);
                getParkDevicesResponse.setSuccess(true);
                getParkDevicesResponse.setStatus("Successfully got the Park's devices");
            }else{
                getParkDevicesResponse.setSite(null);
                getParkDevicesResponse.setSuccess(false);
                getParkDevicesResponse.setStatus("No devices present");
                return getParkDevicesResponse;
            }
        } else {
            getParkDevicesResponse.setStatus("No Park ID specified");
            getParkDevicesResponse.setSite(null);
            getParkDevicesResponse.setSuccess(false);
            return getParkDevicesResponse;
        }
        return getParkDevicesResponse;
    }

    @Override
    public GetDeviceDataResponse getDeviceData(GetDeviceDataRequest request)  {
        if (request==null){
            GetDeviceDataResponse response =  new GetDeviceDataResponse
                    ("Request is null",false);
            return response;
        }
        GetDeviceDataResponse response =  new GetDeviceDataResponse
                ("Failed to load device data for device: " + request.getDeviceName(),false);
        GetDeviceInnerResponse innerResponse;
        if (request.getDeviceName().equals("")){
            response.setSuccess(false);
            response.setStatus("No device name is specified");
            return response;
        }
        if (deviceRepo.findDeviceByDeviceName(request.getDeviceName()).size() != 0) {
            Table waterSourceDataTable = dynamoDB.getTable("WaterSourceData");
            QuerySpec spec = new QuerySpec()
                    .withKeyConditionExpression("deviceName = :id")
                    .withValueMap(new ValueMap()
                            .withString(":id",request.getDeviceName()));
            ItemCollection<QueryOutcome> items = waterSourceDataTable.query(spec);
            Iterator<Item> iterator = items.iterator();
            Item item;
            int counter = 0;
            while (iterator.hasNext() && counter < request.getNumResults()) {
                item = iterator.next();
                counter++;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    innerResponse = mapper.readValue(item.getJSONPretty("WaterSourceData"),GetDeviceInnerResponse.class);
                    innerResponse.setStatus("Success");
                    innerResponse.setSuccess(true);
                    response.addInnerResponse(innerResponse);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            if (response.getInnerResponses()!=null &&response.getInnerResponses().size() > 0) {
                response.setSuccess(true);
                response.setDeviceName(request.getDeviceName());
                response.setStatus("Successfully retrieved data for device: " + response.getDeviceName());
            }
        }else {
            response.setSuccess(false);
            response.setStatus("Device does not exist");
        }
        return response;
    }

    @Override
    public GetAllDevicesResponse getAllDevices() {
        GetAllDevicesResponse response = new GetAllDevicesResponse();

        List<Device> devices = deviceRepo.findAll();

        if (devices.size() > 0) {
            response.setSite(devices);
            response.setSuccess(true);
            response.setStatus("Successfully got all the devices");
        } else {
            response.setStatus("Failed to get all the devices");
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public EditDeviceResponse editDevice(EditDeviceRequest editDeviceRequest) {
        EditDeviceResponse response = new EditDeviceResponse();
        if (editDeviceRequest.getDeviceType().equals("WaterSource")||editDeviceRequest.getDeviceType().equals("Infrastructure")) {
            Optional<Device> deviceToChange = deviceRepo.findById(editDeviceRequest.getDeviceId());
            if (deviceToChange.isPresent()) {
                if (!editDeviceRequest.getDeviceModel().equals("")) {
                    deviceToChange.get().setDeviceModel(editDeviceRequest.getDeviceModel());
                }
                if (!editDeviceRequest.getDeviceName().equals("")) {
                    List<Device> devicesWithSameName = deviceRepo.findDeviceByDeviceName(editDeviceRequest.getDeviceName());
                    if (devicesWithSameName.size() == 0) {
                        deviceToChange.get().setDeviceName(editDeviceRequest.getDeviceName());
                    }
                    else
                    {
                        response.setStatus("A device with that name already exists");
                        response.setSuccess(false);
                        return response;
                    }
                }
                response.setStatus("Device successfully edited.");
                response.setSuccess(true);
                deviceRepo.save(deviceToChange.get());
                return response;
            }
            else {
                response.setStatus("That device does not exist.");
                response.setSuccess(false);
                return response;
            }
        }
        else {
            response.setStatus("The specified device type "+editDeviceRequest.getDeviceType()+" does not exist.");
            response.setSuccess(false);
            return response;
        }
    }

    @Override
    public DeleteDeviceResponse deleteDevice(DeleteDeviceRequest request) {
        if (request.getDeviceId() == null) {
            return new DeleteDeviceResponse("No device id specified.", false);
        }
        Optional<Device> device = deviceRepo.findById(request.getDeviceId());
        if (device.isPresent()) {
            deviceRepo.deleteDevice(device.get().getDeviceId());
            return new DeleteDeviceResponse("Successfully deleted the device and all related entities.", true);
        }
        return new DeleteDeviceResponse("No device with this id exists.", false);    }

    @Override
    public SetMetricFrequencyResponse setMetricFrequency(SetMetricFrequencyRequest request) {
        if (request.getId() == null) {
            return new SetMetricFrequencyResponse("No device id specified.", false);
        }
        Optional<Device> device = deviceRepo.findById(request.getId());
        if (device.isPresent()) {
            if (device.get().getDeviceData().getDeviceConfiguration() != null) {
                for (sensorConfiguration config : device.get().getDeviceData().getDeviceConfiguration()) {
                    if (config.getSettingType().equals("reportingFrequency")) {
                        config.setValue(request.getValue());
                        deviceRepo.save(device.get());
                        return new SetMetricFrequencyResponse("Successfully changed metric frequency to: " +
                                request.getValue() + " hours.", true);
                    }
                }
            }
            else {
                return new SetMetricFrequencyResponse("No device configurations to set.", false);
            }
        }
        return new SetMetricFrequencyResponse("No device configurations set.", false);
    }

}
