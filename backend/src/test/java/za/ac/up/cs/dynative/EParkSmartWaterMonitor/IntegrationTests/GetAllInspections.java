package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.EParkSmartWaterMonitorApplication;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.GetWaterSiteInspectionsRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.GetWaterSiteInspectionsResponse;

import java.util.UUID;


@SpringBootTest(classes = EParkSmartWaterMonitorApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetAllInspections {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void GetInspectionsSiteDNE(){
        GetWaterSiteInspectionsRequest request = new GetWaterSiteInspectionsRequest(UUID.randomUUID());
        ResponseEntity<GetWaterSiteInspectionsResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/inspections/getSiteInspections", request, GetWaterSiteInspectionsResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void GetInspectionsSite(){
        GetWaterSiteInspectionsRequest request = new GetWaterSiteInspectionsRequest(UUID.fromString("886f7187-a436-4105-8bc6-1c340a7232bc"));
        ResponseEntity<GetWaterSiteInspectionsResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/inspections/getSiteInspections", request, GetWaterSiteInspectionsResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }



}

