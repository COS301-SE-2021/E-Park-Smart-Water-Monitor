package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.DeleteParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${app.userName1}")
    private String userName1;
    @Value("${app.userP1}")
    private String userPassword1;
    @Value("${app.parkID}")
    private String parkID;
    @Value("${app.userName3}")

    //post: /api/park/getParkWaterSites
    @Test
    public void getParkWaterSitesIdNull(){
        GetParkSitesRequest request = new GetParkSitesRequest(null);
        ResponseEntity<GetParkSitesResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/park/getParkWaterSites",request,GetParkSitesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No park id specified", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getParkWaterSitesDNE(){
        GetParkSitesRequest request = new GetParkSitesRequest(UUID.randomUUID());
        ResponseEntity<GetParkSitesResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/park/getParkWaterSites",request,GetParkSitesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Park not present", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getParkWaterSites(){
        GetParkSitesRequest request = new GetParkSitesRequest(UUID.fromString(parkID));
        ResponseEntity<GetParkSitesResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/park/getParkWaterSites",request,GetParkSitesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Park Sites and their IoT devices", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }

    //get: /api/park/getAllParksAndSites
    @Test
    public void getAllParksAndSites(){
        ResponseEntity<GetAllParksAndSitesResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .getForEntity("/api/park/getAllParksAndSites",GetAllParksAndSitesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    //get: /api/park/getAllParks
    @Test
    public void getAllParks(){
        ResponseEntity<GetAllParksResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .getForEntity("/api/park/getAllParks",GetAllParksResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    //post: /api/park/editPark
    @Test
    public void editParkIdNull(){
//        EditParkRequest request = new EditParkRequest(null,"","","");
//        ResponseEntity<EditParkResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
//                .postForEntity("/api/park/editPark",request,EditParkResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("No park id specified", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void editParkDNE(){
//        EditParkRequest request = new EditParkRequest(UUID.randomUUID(),"","","");
//        ResponseEntity<EditParkResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
//                .postForEntity("/api/park/editPark",request,EditParkResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("No park with that id exists.", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void editPark(){
//        EditParkRequest request = new EditParkRequest(UUID.fromString(parkID),"Kruger","","");
//        ResponseEntity<EditParkResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
//                .postForEntity("/api/park/editPark",request,EditParkResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Park details changed.", response.getBody().getStatus());
//        assertEquals(true, response.getBody().getSuccess());
//
//        request = new EditParkRequest(UUID.fromString(parkID),"Kruger National Park","","");
//        response = restTemplate.withBasicAuth(userName1,userPassword1)
//                .postForEntity("/api/park/editPark",request,EditParkResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Park details changed.", response.getBody().getStatus());
//        assertEquals(true, response.getBody().getSuccess());
    }

    //post: /api/park/addPark
    @Test
    public void AddParkIncomplete() {
        CreateParkRequest request = new CreateParkRequest("",-25.65475,28.9698512347);
        ResponseEntity<CreateParkResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/park/addPark", request, CreateParkResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No park name specified!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void AddParkDup() {
        CreateParkRequest request = new CreateParkRequest("Rietvlei Nature Reserve",-25.65475,28.9698512347);
        ResponseEntity<CreateParkResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/park/addPark", request, CreateParkResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Park Rietvlei Nature Reserve already exists!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void AddPark() {
        CreateParkRequest request = new CreateParkRequest("IntTesting123123",-25.65475,28.9698512347);
        ResponseEntity<CreateParkResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/park/addPark", request, CreateParkResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Park IntTesting123123 Added!", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());

        DeleteParkRequest requestt = new DeleteParkRequest(response.getBody().getId());
        ResponseEntity<DeleteParkResponse> responsee = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/park/deleteInternal", requestt, DeleteParkResponse.class);
        assertEquals(HttpStatus.OK, responsee.getStatusCode());
        assertEquals(true, response.getBody().getSuccess());

    }

}
