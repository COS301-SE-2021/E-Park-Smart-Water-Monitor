package IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllDevicesResponse;

import java.util.ArrayList;
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
        ResponseEntity<GetAllDevicesResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
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
        ResponseEntity<FindDeviceResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/getById", request, FindDeviceResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No device ID specified", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getDeviceDNE(){
        FindDeviceRequest request = new FindDeviceRequest(UUID.randomUUID());
        ResponseEntity<FindDeviceResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/getById", request, FindDeviceResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device not found", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getDevice(){
        FindDeviceRequest request = new FindDeviceRequest(UUID.fromString("af603639-3406-4362-a7dc-3a1e2b5174a0"));
        ResponseEntity<FindDeviceResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
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
        ResponseEntity<GetNumDevicesResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/getNumDevices", request, GetNumDevicesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(-3, response.getBody().getNumDevices());
        assertEquals(false, response.getBody().isSuccess());
    }

    @Test
    public void getNumDevicesParkDNE(){
        GetNumDevicesRequest request = new GetNumDevicesRequest(UUID.randomUUID());
        ResponseEntity<GetNumDevicesResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
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
    @Test
    public void getParkDevicesIdNull(){
        GetParkDevicesRequest request = new GetParkDevicesRequest(null);
        ResponseEntity<GetParkDevicesResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/getParkDevices", request, GetParkDevicesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No Park ID specified", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getParkDevices(){
        GetParkDevicesRequest request = new GetParkDevicesRequest(UUID.fromString("af603639-3406-4362-a7dc-3a1e2b5174a0"));
        ResponseEntity<GetParkDevicesResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/getParkDevices", request, GetParkDevicesResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully got the Park's devices", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
        assertNotNull(response.getBody().getSite());
    }

    //post: /api/devices/receiveDeviceData
    @Test
    public void receiveDeviceDataNameNull(){
        ReceiveDeviceDataRequest request = new ReceiveDeviceDataRequest("",new ArrayList<>());
        ResponseEntity<ReceiveDeviceDataResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/receiveDeviceData", request, ReceiveDeviceDataResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No device name is specified.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void receiveDeviceData(){
        ReceiveDeviceDataRequest request = new ReceiveDeviceDataRequest("IntTesting123123",new ArrayList<>());
        ResponseEntity<ReceiveDeviceDataResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/receiveDeviceData", request, ReceiveDeviceDataResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device with that name does not exist", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    //put: /api/devices/setMetricFrequency
    /*@Test
    public void setMetricFreqIdNull(){
        SetMetricFrequencyRequest request = new SetMetricFrequencyRequest(null,2);
        ResponseEntity<SetMetricFrequencyResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/setMetricFrequency", request, SetMetricFrequencyResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No device id specified.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void setMetricFreqDeviceDNE(){
        SetMetricFrequencyRequest request = new SetMetricFrequencyRequest(UUID.randomUUID(),2);
        ResponseEntity<SetMetricFrequencyResponse> response = restTemplate.withBasicAuth("testingOne", "test1")
                .postForEntity("/api/devices/setMetricFrequency", request, SetMetricFrequencyResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No device configurations set.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void setMetricFreq(){
        SetMetricFrequencyRequest request = new SetMetricFrequencyRequest(UUID.fromString("cc76aed0-426e-412d-8f9a-f23f857267aa"),2);
        ResponseEntity<SetMetricFrequencyResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/devices/setMetricFrequency", request, SetMetricFrequencyResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully changed metric frequency to: 2.0 hours.", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }*/
}
