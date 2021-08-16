package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetAllParksAndSitesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllDevicesResponse;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/devices/addDevice

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

    //post: /api/devices/getDeviceData

    //post: /api/devices/getNumDevices

    //post: /api/devices/getParkDevices

    //post: /api/devices/receiveDeviceData

    //post: /api/devices/setMetricFrequency


}
