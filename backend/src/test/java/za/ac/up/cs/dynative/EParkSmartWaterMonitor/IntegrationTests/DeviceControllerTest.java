package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.FindDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetAllParksAndSitesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.CreateUserResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllDevicesResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //get: /api/devices/getAllDevices
    @Test
    public void getAllDevices(){
        ResponseEntity<GetAllDevicesResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .getForEntity("/api/devices/getAllDevices",GetAllDevicesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertEquals(true,response.getBody().getSuccess());
        assertEquals("Successfully got all the devices",response.getBody().getStatus());
    }

    //post: /api/devices/getById
    @Test
    public void getDeviceIdNull(){
        FindDeviceRequest request = new FindDeviceRequest(null);
        ResponseEntity<FindDeviceResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/devices/getById", request, FindDeviceResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No device ID specified", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getDeviceDNE(){
        FindDeviceRequest request = new FindDeviceRequest(UUID.randomUUID());
        ResponseEntity<FindDeviceResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/devices/getById", request, FindDeviceResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device not found", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getDevice(){
        FindDeviceRequest request = new FindDeviceRequest(UUID.fromString("cc76aed0-426e-412d-8f9a-f23f857267aa"));
        ResponseEntity<FindDeviceResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/devices/getById", request, FindDeviceResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device found", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
        assertNotNull(response.getBody().getDevice());
    }

    //post: /api/devices/getNumDevices
    @Test
    public void getNumDevicesIdNull(){
        GetNumDevicesRequest request = new GetNumDevicesRequest(null);
        ResponseEntity<GetNumDevicesResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/devices/getNumDevices", request, GetNumDevicesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(-3, response.getBody().getNumDevices());
        assertEquals(false, response.getBody().isSuccess());
    }

    @Test
    public void getNumDevicesParkDNE(){
        GetNumDevicesRequest request = new GetNumDevicesRequest(UUID.randomUUID());
        ResponseEntity<GetNumDevicesResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/devices/getNumDevices", request, GetNumDevicesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(-2, response.getBody().getNumDevices());
        assertEquals(false, response.getBody().isSuccess());
    }

   /* @Test
    public void getNumDevices(){
        UUID id = UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80");
        GetNumDevicesRequest request = new GetNumDevicesRequest(id);
        ResponseEntity<GetNumDevicesResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/devices/getNumDevices", request, GetNumDevicesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(-2, response.getBody().getNumDevices());
        assertNotEquals(-3, response.getBody().getNumDevices());
        assertEquals(true, response.getBody().isSuccess());
    }*/

    //post: /api/devices/getParkDevices

    //post: /api/devices/receiveDeviceData

    //post: /api/devices/setMetricFrequency


}
