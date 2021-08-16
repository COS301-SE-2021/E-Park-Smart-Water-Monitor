package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetAllParksAndSitesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetAllParksResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetParkSitesResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/park/getParkWaterSites
    @Test
    public void getParkWaterSitesIdNull(){
        GetParkSitesRequest request = new GetParkSitesRequest(null);
        ResponseEntity<GetParkSitesResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/park/getParkWaterSites",request,GetParkSitesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No park id specified", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getParkWaterSitesDNE(){
        GetParkSitesRequest request = new GetParkSitesRequest(UUID.randomUUID());
        ResponseEntity<GetParkSitesResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/park/getParkWaterSites",request,GetParkSitesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Park not present", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getParkWaterSites(){
        GetParkSitesRequest request = new GetParkSitesRequest(UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80"));
        ResponseEntity<GetParkSitesResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/park/getParkWaterSites",request,GetParkSitesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Park Sites and their IoT devices", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }

    //get: /api/park/getAllParksAndSites
    @Test
    public void getAllParksAndSites(){
        ResponseEntity<GetAllParksAndSitesResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .getForEntity("/api/park/getAllParksAndSites",GetAllParksAndSitesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    //get: /api/park/getAllParks
    @Test
    public void getAllParks(){
        ResponseEntity<GetAllParksResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .getForEntity("/api/park/getAllParks",GetAllParksResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    //post: /api/park/editPark

    //post: /api/park/addPark

    //delete: /api/park/deletePark

}
