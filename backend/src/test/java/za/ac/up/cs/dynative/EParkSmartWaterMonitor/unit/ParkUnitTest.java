package za.ac.up.cs.dynative.EParkSmartWaterMonitor.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.CreateParkResponse;

public class ParkUnitTest extends UnitTestBaseClass {

    @Test
    public void testCreatePark() throws JsonProcessingException {
        LOGGER.info("Testing CreateParkRequest construction");

        String testParkName = "testPark";
        double testParkLatitude = -12.34;;
        double testParkLongitude = -56.78;

        String jsonData = "{"
                + "\"parkName\" : \""
                + testParkName
                + "\","
                + "\"latitude\" : "
                + testParkLatitude
                + ","
                + "\"longitude\" : "
                + testParkLongitude
                + "}";

        ObjectMapper mapper = new ObjectMapper();

        CreateParkRequest request = mapper.readValue(jsonData, CreateParkRequest.class);
        assert (request != null);

        LOGGER.info("DONE Testing CreateParkRequest construction");


        LOGGER.info("Testing createPark");

        CreateParkResponse response = parkService.createPark(request);
        assert (response != null);

        LOGGER.info("DONE Testing createPark");


        LOGGER.info("CreateParkResponse status: " + response.getStatus());
        LOGGER.info("CreateParkResponse success: " + response.getSuccess());
    }
}
