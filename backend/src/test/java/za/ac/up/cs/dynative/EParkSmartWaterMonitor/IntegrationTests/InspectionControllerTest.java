package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests.GetAllInspections;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.AddInspectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.AddInspectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.GetAllInspectionsResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetAllParksAndSitesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetParkSitesResponse;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InspectionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/inspections/setStatus

    //post: /api/inspections/getSiteInspections

    //post: /api/inspections/setComments

    //post: /api/inspections/getDeviceInspections

    //get: /api/inspections/getAllInspections
    @Test
    public void getAllInspections(){
        ResponseEntity<GetAllInspectionsResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .getForEntity("/api/inspections/getAllInspections",GetAllInspectionsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }

    //post: /api/inspections/addInspection
    @Test
    public void addInspectionDeviceIdNull(){
        AddInspectionRequest request = new AddInspectionRequest(null, new Date(), "Integration Testing");
        ResponseEntity<AddInspectionResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/addInspection",request,AddInspectionResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to add inspection! No deviceId specified!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void addInspectionDeviceDNE(){
        AddInspectionRequest request = new AddInspectionRequest(UUID.randomUUID(), new Date(), "Integration Testing");
        ResponseEntity<AddInspectionResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/addInspection",request,AddInspectionResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to add inspection! Failure to get device!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void addInspection(){
        AddInspectionRequest request = new AddInspectionRequest(UUID.fromString("cc76aed0-426e-412d-8f9a-f23f857267aa"), new Date(1638309600), "Integration Testing"+ new Date().toString());
        ResponseEntity<AddInspectionResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/addInspection",request,AddInspectionResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inspection successfully added!", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }
}
