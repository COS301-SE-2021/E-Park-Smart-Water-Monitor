package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.*;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InspectionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/inspections/setStatus
    @Test
    public void setStatusIdNull(){
        SetInspectionStatusRequest request = new SetInspectionStatusRequest(null,"");
        ResponseEntity<SetInspectionStatusResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/setStatus",request,SetInspectionStatusResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to set inspection status! Invalid inspectionId!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void setStatusInspectionDNE(){
        SetInspectionStatusRequest request = new SetInspectionStatusRequest(UUID.randomUUID(),"");
        ResponseEntity<SetInspectionStatusResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/setStatus",request,SetInspectionStatusResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to set inspection status! Inspection not found!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void setStatusInspection(){
        SetInspectionStatusRequest request = new SetInspectionStatusRequest(UUID.fromString("7cd82d9d-8a1f-4ca5-b334-6522f2fc845b"),"completed");
        ResponseEntity<SetInspectionStatusResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/setStatus",request,SetInspectionStatusResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inspection status successfully set!", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
        assertNotNull(response);
    }

    //post: /api/inspections/getSiteInspections
    @Test
    public void getWaterSiteInspectionIdNull(){
        GetWaterSiteInspectionsRequest request = new GetWaterSiteInspectionsRequest(null);
        ResponseEntity<GetWaterSiteInspectionsResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/getSiteInspections",request,GetWaterSiteInspectionsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to get inspection! Invalid waterSiteId!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getWaterSiteInspection(){
        GetWaterSiteInspectionsRequest request = new GetWaterSiteInspectionsRequest(UUID.fromString("91d05eb1-2a35-4e44-9726-631d83121edb"));
        ResponseEntity<GetWaterSiteInspectionsResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/getSiteInspections",request,GetWaterSiteInspectionsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inspections retrieved successfully!", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
        assertNotNull(response);
    }

    //post: /api/inspections/setComments
    @Test
    public void setCommentsIdNull(){
        SetInspectionCommentsRequest request = new SetInspectionCommentsRequest(null,"Integration testing is going good ;)");
        ResponseEntity<SetInspectionCommentsResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/setComments",request,SetInspectionCommentsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to set inspection comments! Invalid inspectionId!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void setCommentsInspectionDNE(){
        SetInspectionCommentsRequest request = new SetInspectionCommentsRequest(UUID.randomUUID(),"Integration testing is going good ;)");
        ResponseEntity<SetInspectionCommentsResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/setComments",request,SetInspectionCommentsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to set inspection comments! Inspection not found!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void setComments(){
        SetInspectionCommentsRequest request = new SetInspectionCommentsRequest(UUID.fromString("7cd82d9d-8a1f-4ca5-b334-6522f2fc845b"),"Integration testing is going good ;)");
        ResponseEntity<SetInspectionCommentsResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/setComments",request,SetInspectionCommentsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inspection comments successfully set!", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
        assertNotNull(response);
    }

    //post: /api/inspections/getDeviceInspections
    @Test
    public void getDeviceInspectionIdNull(){
        GetDeviceInspectionsRequest request = new GetDeviceInspectionsRequest(null);
        ResponseEntity<GetDeviceInspectionsResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/getDeviceInspections",request,GetDeviceInspectionsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failed to get inspection! Invalid deviceId!", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getDeviceInspection(){
        GetDeviceInspectionsRequest request = new GetDeviceInspectionsRequest(UUID.fromString("cc76aed0-426e-412d-8f9a-f23f857267aa"));
        ResponseEntity<GetDeviceInspectionsResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/getDeviceInspections",request,GetDeviceInspectionsResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inspections retrieved successfully!", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
        assertNotNull(response.getBody().getInspectionList());
    }

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
        AddInspectionRequest request = new AddInspectionRequest(UUID.fromString("cc76aed0-426e-412d-8f9a-f23f857267aa"), new Date(), "Integration Testing:  "+ new Date().toString());
        ResponseEntity<AddInspectionResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/inspections/addInspection",request,AddInspectionResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inspection successfully added!", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }
}
