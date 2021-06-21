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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.SMSRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.SMSResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = EParkSmartWaterMonitorApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SendSMS {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void sendSMSNoRec(){
        ArrayList<User> receivers= new ArrayList<>();
        SMSRequest request = new SMSRequest(receivers,"Not going to work");
        ResponseEntity<SMSResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/notifications/sms", request, SMSResponse.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void sendSMSUserDNE(){
        ArrayList<User> receivers= new ArrayList<>();
        receivers.add(new User());
        SMSRequest request = new SMSRequest(receivers,"Not going to work");
        ResponseEntity<SMSResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/notifications/sms", request, SMSResponse.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

   /* @Test
    @Order(3)
    public void sendSMS(){
        ArrayList<User> receivers= new ArrayList<>();
        User u= new User();
        u.setId(UUID.fromString("48356a22-e568-484b-bc5d-4aa35b07e86a"));
        receivers.add(u);
        SMSRequest request = new SMSRequest(receivers,"This works");
        ResponseEntity<SMSResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/notifications/sms", request, SMSResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }*/

}

