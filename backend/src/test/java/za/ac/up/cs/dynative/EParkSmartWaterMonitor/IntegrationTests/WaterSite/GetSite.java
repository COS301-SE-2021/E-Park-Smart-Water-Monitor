package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests.WaterSite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetSite {
    //post: /api/sites/getSite

    @Autowired
    private TestRestTemplate restTemplate;

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


}
