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
import software.amazon.awssdk.services.iot.IotClientBuilder;
import software.amazon.awssdk.services.iot.model.AttributePayload;
import software.amazon.awssdk.services.iot.model.CreateThingRequest;
import software.amazon.awssdk.services.iot.model.CreateThingResponse;
import software.amazon.awssdk.services.iotdataplane.IotDataPlaneClient;
import software.amazon.awssdk.services.iotdataplane.model.UpdateThingShadowRequest;
import software.amazon.awssdk.services.iotdataplane.model.UpdateThingShadowResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.InfrastructureDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.InfrastructureDeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.WaterSourceDeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.DeleteUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.DeleteUserResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.CanAttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AttachWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.CanAttachWaterSourceDeviceResponse;

import java.util.*;

@Service("DeviceServiceImpl")
public class DevicesServicesImpl implements DevicesService {

    private WaterSourceDeviceRepo waterSourceDeviceRepo;
    private InfrastructureDeviceRepo infrastructureDeviceRepo;
    private ParkService parkService;
    private WaterSiteService waterSiteService;
    private MeasurementRepo measurementRepo;
    private IotClient iotClient;
    private IotDataPlaneClient iotDataPlaneClient;
    private AmazonDynamoDB dynamoDBClient;
    private DynamoDB dynamoDB;

    public DevicesServicesImpl(@Qualifier("WaterSourceDeviceRepo") WaterSourceDeviceRepo waterSourceDeviceRepo,
                               @Qualifier("InfrastructureDeviceRepo") InfrastructureDeviceRepo infrastructureDeviceRepo,
                               @Qualifier("ParkService") ParkService parkService,
                               @Qualifier("WaterSiteServiceImpl") WaterSiteService waterSiteService,
                               @Qualifier("SourceDataRepo") MeasurementRepo measurementRepo) {
        this.waterSourceDeviceRepo = waterSourceDeviceRepo;
        this.infrastructureDeviceRepo = infrastructureDeviceRepo;
        this.parkService = parkService;
        this.measurementRepo = measurementRepo;
        this.waterSiteService = waterSiteService;
        this.iotClient = IotClient.builder().region(Region.US_EAST_2).build();
        this.iotDataPlaneClient = IotDataPlaneClient.builder().region(Region.US_EAST_2).build();
        this.dynamoDBClient = AmazonDynamoDBClient.builder().withRegion(String.valueOf(Region.US_EAST_2)).build();
        this.dynamoDB = new DynamoDB(dynamoDBClient);
    }

    public Collection<WaterSourceDevice> getAll() {
        return waterSourceDeviceRepo.findAll();
    }

    public AddWaterSourceDeviceResponse addDevice(AddWaterSourceDeviceRequest addWSDRequest) {
        AddWaterSourceDeviceResponse response = new AddWaterSourceDeviceResponse();
        List<WaterSourceDevice> devices = waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName(addWSDRequest.getDeviceName());

        if (devices.size() == 0) {

            CanAttachWaterSourceDeviceResponse canAttachWaterSourceDeviceResponse = waterSiteService.canAttachWaterSourceDevice(new CanAttachWaterSourceDeviceRequest(addWSDRequest.getSiteId()));

            if (!canAttachWaterSourceDeviceResponse.getSuccess()) {
                response.setSuccess(false);
                response.setStatus("The water site " + addWSDRequest.getSiteId() + " does not exist.");
            } else {

                Map<String, String> attributes = Map.of("deviceModel",addWSDRequest.getDeviceModel());

                AttributePayload attributePayload = AttributePayload.builder()
                        .attributes(attributes)
                        .build();

                CreateThingRequest createThingRequest = CreateThingRequest.builder()
                        .thingName(addWSDRequest.getDeviceName())
                        .thingTypeName("WaterSourceDevice")
                        .attributePayload(attributePayload)
                        .build();

                CreateThingResponse createThingResponse = iotClient.createThing(createThingRequest);

                WaterSourceDevice newDevice = new WaterSourceDevice(UUID.fromString(createThingResponse.thingId()),addWSDRequest.getDeviceName(), addWSDRequest.getDeviceModel(), addWSDRequest.getLongitude(), addWSDRequest.getLatitude());
                AttachWaterSourceDeviceResponse attachWaterSourceDeviceResponse = waterSiteService.attachWaterSourceDevice(new AttachWaterSourceDeviceRequest(addWSDRequest.getSiteId(), newDevice));

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

                waterSourceDeviceRepo.save(newDevice);
                response.setSuccess(true);
                response.setStatus("Device " + addWSDRequest.getDeviceName() + " successfully added");
            }

        } else {
            response.setSuccess(false);
            response.setStatus("Device " + addWSDRequest.getDeviceName() + " already exists.");
        }

        return response;

    }

    public FindDeviceResponse findDevice(FindDeviceRequest findDeviceRequest)
    {

        Optional<WaterSourceDevice> device = waterSourceDeviceRepo.findById(findDeviceRequest.getDeviceID());
        if (device.isPresent())
        {
            return new FindDeviceResponse("Device found",true,device.get());
        }
        else
            return new FindDeviceResponse("Device not found",false,null);

    }

    @Override
    public ReceiveDeviceDataResponse receiveWaterDeviceData(ReceiveDeviceDataRequest request) {
        List<WaterSourceDevice> devices = waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName(request.getDeviceName());
        ReceiveDeviceDataResponse response = new ReceiveDeviceDataResponse();

        WaterSourceDevice device = null;
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
            waterSourceDeviceRepo.save(device);


            response.setStatus(
                    "Successfully added data send from ESP: "
                            + request.getDeviceName()
                            + " sent at: "
                            + request.getMeasurements().get(0).getDeviceDateTime());
            response.setSuccess(true);

        } else {
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

                getNumDevicesResponse.setNumDevices(waterSourceDeviceRepo.getAllParkDevices(request.getParkId()).size());
                getNumDevicesResponse.setSuccess(true);
            }
        } else getNumDevicesResponse.setSuccess(false);
        return getNumDevicesResponse;
    }

    @Override
    public GetParkDevicesResponse getParkDevices(GetParkDevicesRequest request) {
        GetParkDevicesResponse getParkDevicesResponse = new GetParkDevicesResponse();
        if (request.getParkId() != null) {

            List<WaterSourceDevice> devices = waterSourceDeviceRepo.findAll();

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

    @Override
    public EditDeviceResponse editDevice(EditDeviceRequest editDeviceRequest) {
        EditDeviceResponse response = new EditDeviceResponse();
        if (editDeviceRequest.getDeviceType().equals("WaterSource")) {

            Optional<WaterSourceDevice> waterSourceDeviceToChange = waterSourceDeviceRepo.findById(editDeviceRequest.getDeviceId());

            if (waterSourceDeviceToChange.isPresent()) {
                if (!editDeviceRequest.getDeviceModel().equals("")) {
                    waterSourceDeviceToChange.get().setDeviceModel(editDeviceRequest.getDeviceModel());
                }
                if (!editDeviceRequest.getDeviceName().equals("")) {

                    List<WaterSourceDevice> devicesWithSameName = waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName(editDeviceRequest.getDeviceName());
                    if (devicesWithSameName.size() == 0) {
                        waterSourceDeviceToChange.get().setDeviceName(editDeviceRequest.getDeviceName());

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
                waterSourceDeviceRepo.save(waterSourceDeviceToChange.get());
                return response;
            }
            else
            {
                response.setStatus("That device does not exist.");
                response.setSuccess(false);
                return response;

            }



        }
        else if (editDeviceRequest.getDeviceType().equals("Infrastructure"))
        {
            Optional<InfrastructureDevice> infrastructureDeviceToChange = infrastructureDeviceRepo.findById(editDeviceRequest.getDeviceId());


            if (infrastructureDeviceToChange.isPresent()) {
                if (!editDeviceRequest.getDeviceModel().equals("")) {
                    infrastructureDeviceToChange.get().setDeviceModel(editDeviceRequest.getDeviceModel());
                }
                if (!editDeviceRequest.getDeviceName().equals("")) {

                    List<InfrastructureDevice> devicesWithSameName = infrastructureDeviceRepo.findInfrastructureDeviceByDeviceName(editDeviceRequest.getDeviceName());
                    if (devicesWithSameName.size() == 0) {
                        infrastructureDeviceToChange.get().setDeviceName(editDeviceRequest.getDeviceName());

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
                infrastructureDeviceRepo.save(infrastructureDeviceToChange.get());
                return response;
            }
            else
            {
                response.setStatus("That device does not exist.");
                response.setSuccess(false);
                return response;

            }

        }
        else
        {
            response.setStatus("The specified device type "+editDeviceRequest.getDeviceType()+" does not exist.");
            response.setSuccess(false);
            return response;
        }

    }

    @Override
    public GetDeviceDataResponse getDeviceData(GetDeviceDataRequest request) {
        GetDeviceDataResponse response =  new GetDeviceDataResponse("Failed to load device data for device: " + request.getDeviceName(),false);
        GetDeviceInnerResponse innerResponse;

        if (waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName(request.getDeviceName()).size() == 0) {
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
            if (response.getInnerResponses().size() > 0) {
                response.setSuccess(true);
                response.setDeviceName(request.getDeviceName());
                response.setStatus("Successfully retrieved data for device: " + response.getDeviceName());
            }
        }

        return response;
    }

    @Override
    public GetAllDevicesResponse getAllDevices() {
        GetAllDevicesResponse response = new GetAllDevicesResponse();

        List<WaterSourceDevice> devices = waterSourceDeviceRepo.findAll();

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
}
