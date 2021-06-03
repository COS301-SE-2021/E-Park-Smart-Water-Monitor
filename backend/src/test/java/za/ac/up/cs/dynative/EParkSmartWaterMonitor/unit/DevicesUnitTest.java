package za.ac.up.cs.dynative.EParkSmartWaterMonitor.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.AddWaterSourceDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;

import java.util.UUID;

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
    public void testGetNumDevices() throws JsonProcessingException {
        LOGGER.info("Testing GetNumDevicesRequest construction");

        String parkName = "testPark";

        String jsonData = "{"
                + "\"parkName\" : \""
                + parkName
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
