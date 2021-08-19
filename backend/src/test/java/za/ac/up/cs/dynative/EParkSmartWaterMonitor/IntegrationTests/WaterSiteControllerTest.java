package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.EditWaterSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.EditWaterSiteResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WaterSiteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private UUID siteId;

    //post: /api/sites/getSite
    @Test
    public void getSiteIdNull(){
        GetSiteByIdRequest request = new GetSiteByIdRequest(null);
        ResponseEntity<GetSiteByIdResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/sites/getSite", request,GetSiteByIdResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("No id specified",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void getSiteDNE(){
        GetSiteByIdRequest request = new GetSiteByIdRequest(UUID.randomUUID());
        ResponseEntity<GetSiteByIdResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/sites/getSite", request,GetSiteByIdResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("Site does not exist.",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void getSite(){
        GetSiteByIdRequest request = new GetSiteByIdRequest(UUID.fromString("91d05eb1-2a35-4e44-9726-631d83121edb"));
        ResponseEntity<GetSiteByIdResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/sites/getSite", request,GetSiteByIdResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("Successfully found site.",response.getBody().getStatus());
        assertEquals(true,response.getBody().getSuccess());
    }

    //post: /api/sites/editWaterSite
    @Test
    public void editWaterSiteIdNull(){
        EditWaterSiteRequest request = new EditWaterSiteRequest(null,"",90,9);
        ResponseEntity<EditWaterSiteResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/sites/editWaterSite", request,EditWaterSiteResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("No watersite id specified",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void editWaterSiteDNE(){
        EditWaterSiteRequest request = new EditWaterSiteRequest(UUID.randomUUID(),"",90,9);
        ResponseEntity<EditWaterSiteResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/sites/editWaterSite", request,EditWaterSiteResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Watersite does not exist.",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void editWaterSite(){
        EditWaterSiteRequest request = new EditWaterSiteRequest(UUID.fromString("91d05eb1-2a35-4e44-9726-631d83121edb"),"ROT Water Site",-25.892494434,28.289765508);
        ResponseEntity<EditWaterSiteResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/sites/editWaterSite", request,EditWaterSiteResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Watersite successfully edited.",response.getBody().getStatus());
        assertEquals(true,response.getBody().getSuccess());
    }

    //post: /api/sites/addSite
    @Test
    public void addWaterSiteParkIdNull(){
        AddSiteRequest request = new AddSiteRequest(null,"IntTesting123123",-25.6637895,28.271731499999998);
        ResponseEntity<AddSiteResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/sites/addSite", request,AddSiteResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("No park id specified",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void addWaterSiteSuccess(){
        UUID id = UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80");
        AddSiteRequest request = new AddSiteRequest(id,"IntTesting123123",-25.6637895,28.271731499999998);
        ResponseEntity<AddSiteResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/sites/addSite", request,AddSiteResponse.class);
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertEquals("Successfully added: IntTesting123123",response.getBody().getStatus());
        assertEquals(true,response.getBody().getSuccess());
        siteId = response.getBody().getSite().getId();
    }

}
