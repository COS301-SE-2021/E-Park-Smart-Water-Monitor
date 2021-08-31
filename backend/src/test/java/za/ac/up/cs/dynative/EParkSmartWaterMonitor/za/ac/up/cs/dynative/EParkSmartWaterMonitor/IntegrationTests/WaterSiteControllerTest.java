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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.DeleteWaterSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.DeleteWaterSiteResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WaterSiteControllerTest {

    @Value("${app.watersiteID}")
    private String waterSiteID;
    @Value("${app.userName1}")
    private String userName1;
    @Value("${app.userP1}")
    private String userPassword1;
    @Value("${app.parkID}")
    private String parkID;

    @Autowired
    private TestRestTemplate restTemplate;

    private UUID siteId;

    //post: /api/sites/getSite
    @Test
    public void getSiteIdNull(){
        GetSiteByIdRequest request = new GetSiteByIdRequest(null);
        ResponseEntity<GetSiteByIdResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/sites/getSite", request,GetSiteByIdResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("No id specified",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void getSiteDNE(){
        GetSiteByIdRequest request = new GetSiteByIdRequest(UUID.randomUUID());
        ResponseEntity<GetSiteByIdResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/sites/getSite", request,GetSiteByIdResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("Site does not exist.",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void getSite(){
        GetSiteByIdRequest request = new GetSiteByIdRequest(UUID.fromString(waterSiteID));
        ResponseEntity<GetSiteByIdResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/sites/getSite", request,GetSiteByIdResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("Successfully found site.",response.getBody().getStatus());
        assertEquals(true,response.getBody().getSuccess());
    }

    //post: /api/sites/editWaterSite
    @Test
    public void editWaterSiteIdNull(){
//        EditWaterSiteRequest request = new EditWaterSiteRequest(null,"",90,9);
//        ResponseEntity<EditWaterSiteResponse> response = restTemplate.withBasicAuth(userName1",userPassword1")
//                .postForEntity("/api/sites/editWaterSite", request,EditWaterSiteResponse.class);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("No watersite id specified",response.getBody().getStatus());
//        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void editWaterSiteDNE(){
//        EditWaterSiteRequest request = new EditWaterSiteRequest(UUID.randomUUID(),"",90,9);
//        ResponseEntity<EditWaterSiteResponse> response = restTemplate.withBasicAuth(userName1",userPassword1")
//                .postForEntity("/api/sites/editWaterSite", request,EditWaterSiteResponse.class);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Watersite does not exist.",response.getBody().getStatus());
//        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void editWaterSite(){
//        EditWaterSiteRequest request = new EditWaterSiteRequest(UUID.fromString("91d05eb1-2a35-4e44-9726-631d83121edb"),"ROT Water Site",-25.892494434,28.289765508);
//        ResponseEntity<EditWaterSiteResponse> response = restTemplate.withBasicAuth(userName1,userPassword1")
//                .postForEntity("/api/sites/editWaterSite", request,EditWaterSiteResponse.class);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Watersite successfully edited.",response.getBody().getStatus());
//        assertEquals(true,response.getBody().getSuccess());
    }

    //post: /api/sites/addSite
    @Test
    public void addWaterSiteParkIdNull(){
        AddSiteRequest request = new AddSiteRequest(null,"IntTesting123123",-25.6637895,28.271731499999998,"circle",0,0,1);
        ResponseEntity<AddSiteResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/sites/addSite", request,AddSiteResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("No park id specified",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void addWaterSiteSuccess(){
        UUID id = UUID.fromString(parkID);
        AddSiteRequest request = new AddSiteRequest(id,"IntTesting123123",-25.6637895,28.271731499999998,"circle", 0,0,1);
        ResponseEntity<AddSiteResponse> response = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/sites/addSite", request,AddSiteResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("Successfully added: IntTesting123123",response.getBody().getStatus());
        assertEquals(true,response.getBody().getSuccess());

        id=response.getBody().getId();

        DeleteWaterSiteRequest requestt = new DeleteWaterSiteRequest(id);
        requestt.setWaterSiteRequestId(response.getBody().getId());
        ResponseEntity<DeleteWaterSiteResponse> responsee = restTemplate.withBasicAuth(userName1,userPassword1)
                .postForEntity("/api/sites/deleteInternal", requestt,DeleteWaterSiteResponse.class);
        assertEquals(HttpStatus.OK,responsee.getStatusCode());
//        assertEquals(true,responsee.getBody().getSuccess());
    }

}
