package za.ac.up.cs.dynative.EParkSmartWaterMonitor.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.SaveParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.CreateParkResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetParkSitesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.SaveParkResponse;

import java.util.UUID;

@SpringBootTest
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

    @Test
    public void testFindParkByName() throws JsonProcessingException {
        LOGGER.info("Testing FindByParkNameRequest construction");

        String testParkName = "testPark";

        String jsonData = "{"
                + "\"parkName\" : \""
                + testParkName
                + "\""
                + "}";

        ObjectMapper mapper = new ObjectMapper();

        FindByParkNameRequest request = mapper.readValue(jsonData, FindByParkNameRequest.class);
        assert (request != null);

        LOGGER.info("DONE Testing FindByParkNameRequest construction");


        LOGGER.info("Testing findParkByName");

        FindByParkNameResponse response = parkService.findParkByName(request);
        assert (response != null);

        LOGGER.info("DONE Testing findParkByName");

        if (response.getPark() == null) {
            LOGGER.info("No park found");
        }
        else {
            LOGGER.info("FindByParkNameResponse park name: " + response.getPark().getParkName());
        }
    }

    @Test
    public void testSavePark() {
        LOGGER.info("Testing SaveParkRequest construction");

        String parkName = "testPark";
        double latitude = 13.24;
        double longitude = 41.32;
        Park testPark = new Park(parkName, latitude, longitude);

        SaveParkRequest request = new SaveParkRequest(testPark);
        assert (request != null);

        LOGGER.info("DONE Testing SaveParkRequest construction");

        LOGGER.info("Testing savePark");

        SaveParkResponse response = parkService.savePark(request);
        assert (response != null);

        LOGGER.info("DONE Testing savePark");

        LOGGER.info("SaveParkResponse status: " + response.getStatus());
        LOGGER.info("SaveParkResponse success: " + response.isSuccess());
    }

    @Test
    public void testGetParkWaterSites() throws JsonProcessingException {
        LOGGER.info("Testing GetParkSitesRequest construction");

        UUID parkId = UUID.randomUUID();

        String jsonData = "{"
                + "\"parkId\" : \""
                + parkId
                + "\""
                + "}";

        ObjectMapper mapper = new ObjectMapper();

        GetParkSitesRequest request = mapper.readValue(jsonData, GetParkSitesRequest.class);
        assert (request != null);

        LOGGER.info("DONE Testing GetParkSitesRequest construction");


        LOGGER.info("Testing getParkWaterSites");

        GetParkSitesResponse response = parkService.getParkWaterSites(request);
        assert (response != null);

        LOGGER.info("DONE Testing getParkWaterSites");

        LOGGER.info("SaveParkResponse status: " + response.getStatus());
        LOGGER.info("SaveParkResponse success: " + response.getSuccess());
    }
}
