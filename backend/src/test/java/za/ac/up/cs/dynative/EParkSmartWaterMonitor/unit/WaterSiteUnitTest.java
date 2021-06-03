package za.ac.up.cs.dynative.EParkSmartWaterMonitor.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;

public class WaterSiteUnitTest extends UnitTestBaseClass {
    @Test
    public void testAddSite() throws JsonProcessingException {
        LOGGER.info("Testing AddSiteRequest construction");

        String parkName = "testPark";
        String siteName = "testSite";
        double latitude = 98.76;
        double longitude = 54.32;

        String jsonData = "{"
                + "\"parkName\" : \""
                + parkName
                + "\","
                + "\"siteName\" : \""
                + siteName
                + "\","
                + "\"latitude\" : \""
                + latitude
                + "\","
                + "\"longitude\" : \""
                + longitude
                + "\""
                + "}";

        ObjectMapper mapper = new ObjectMapper();

        AddSiteRequest request = mapper.readValue(jsonData, AddSiteRequest.class);
        assert (request != null);

        LOGGER.info("DONE Testing AddSiteRequest construction");

        LOGGER.info("Testing addSite");

        AddSiteResponse response = waterSiteService.addSite(request);
        assert (response != null);

        LOGGER.info("DONE Testing addSite");

        LOGGER.info("AddSiteResponse status: " + response.getStatus());
        LOGGER.info("AddSiteResponse success: " + response.getSuccess());
    }
}
