package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests.extra;

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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.DeleteDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.FindDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.DeleteDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import java.util.UUID;

@SpringBootTest(classes = EParkSmartWaterMonitorApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeleteDevice {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void deleteDeviceIdNull(){
        DeleteDeviceRequest request= new DeleteDeviceRequest(null);
        ResponseEntity<DeleteDeviceResponse> response = restTemplate.postForEntity("http://localhost:" + port + "/api/devices/deleteDevice",request, DeleteDeviceResponse.class);
        assertNotNull(response);
        assertEquals("No device id specified.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    @Order(2)
    public void deleteDeviceDNE(){

    }

    @Test
    @Order(3)
    public void deleteDeviceSuccess(){

    }
}
