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


}
