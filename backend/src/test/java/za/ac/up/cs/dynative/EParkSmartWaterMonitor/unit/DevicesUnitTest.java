package za.ac.up.cs.dynative.EParkSmartWaterMonitor.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.util.JSONPObject;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.AddWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.ReceiveDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class DevicesUnitTest extends UnitTestBaseClass {
    @Test
    public void testAddDevice() throws JsonProcessingException {
        LOGGER.info("Testing AddWaterSourceDeviceRequest construction");

        UUID deviceId = UUID.randomUUID();
        UUID siteId = UUID.randomUUID();
        String  deviceModel = "ESP32";
        String  deviceName = "Test001";
        double  longitude = 12.34;
        double  latitude = -56.78;
        String parkName = "testPark";

        String jsonData = "{"
                + "\"parkName\" : \""
                + parkName
                + "\","
                + "\"siteId\" : \""
                + siteId
                + "\","
                + "\"deviceModel\" : \""
                + deviceModel
                + "\","
                + "\"deviceName\" : \""
                + deviceName
                + "\","
                + "\"longitude\" : \""
                + longitude
                + "\","
                + "\"latitude\" : \""
                + latitude
                + "\""
                + "}";

        ObjectMapper mapper = new ObjectMapper();

        AddWaterSourceDeviceRequest request = mapper.readValue(jsonData, AddWaterSourceDeviceRequest.class);
        assert (request != null);

        LOGGER.info("DONE Testing AddWaterSourceDeviceRequest construction");

        LOGGER.info("Testing addDevice");

        AddWaterSourceDeviceResponse response = devicesService.addDevice(request);
        assert (response != null);

        LOGGER.info("DONE Testing addDevice");

        LOGGER.info("AddWaterSourceDeviceResponse status: " + response.getStatus());
        LOGGER.info("AddWaterSourceDeviceResponse success: " + response.getSuccess());
    }

    @Test
    public void testReceiveWaterDeviceData() throws JsonProcessingException {
        LOGGER.info("Testing ReceiveDeviceDataRequest construction");

        String deviceName = "testDevice";
        String type = "WATER_TEMP";
        double value = 20.9;
        String unitOfMeasurement = "Centigrade";
        String deviceDateTime = "1-1-1997";

        String jsonData = "{"
            + "\"deviceName\" : \"" + deviceName + "\","
            + "\"measurements\" : ["
                + "{"
                    + "\"type\" : \"" + type + "\","
                    + "\"value\" : \"" + value + "\","
                    + "\"unitOfMeasurement\" : \"" + unitOfMeasurement + "\","
                    + "\"deviceDateTime\": \"" + deviceDateTime + "\""
                + "}"
            + "]"
        + "}";

        ObjectMapper mapper = new ObjectMapper();

        ReceiveDeviceDataRequest request = mapper.readValue(jsonData, ReceiveDeviceDataRequest.class);
        assert (request != null);

        LOGGER.info("DONE Testing ReceiveDeviceDataRequest construction");

        LOGGER.info("Testing receiveWaterDeviceData");

        ReceiveDeviceDataResponse response = devicesService.receiveWaterDeviceData(request);
        assert (response != null);

        LOGGER.info("DONE Testing receiveWaterDeviceData");

        LOGGER.info("ReceiveDeviceDataResponse status: " + response.getStatus());
        LOGGER.info("ReceiveDeviceDataResponse success: " + response.getSuccess());
    }


   @Test
    public void testGetNumDevices() throws JsonProcessingException, InvalidRequestException {
        LOGGER.info("Testing GetNumDevicesRequest construction");

        UUID parkId = UUID.fromString("190c4aa9-a55f-4118-b26b-dc537e0a6f30");

        String jsonData = "{"
                + "\"parkId\" : \""
                + parkId
                + "\""
                + "}";

        ObjectMapper mapper = new ObjectMapper();

        GetNumDevicesRequest request = mapper.readValue(jsonData, GetNumDevicesRequest.class);
        assert (request != null);

        LOGGER.info("DONE Testing GetNumDevicesRequest construction");

        LOGGER.info("Testing getNumDevices");

        GetNumDevicesResponse response = devicesService.getNumDevices(request);
        assert (response != null);

        LOGGER.info("DONE Testing getNumDevices");

        LOGGER.info("GetNumDevicesResponse numDevices: " + response.getNumDevices());
        LOGGER.info("GetNumDevicesResponse success: " + response.isSuccess());
    }
}
