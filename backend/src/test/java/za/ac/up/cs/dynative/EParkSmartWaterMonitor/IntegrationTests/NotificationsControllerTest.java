package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.models.Topic;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.EmailResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetParkSitesResponse;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/notifications/mail
    @Test
    public void sendMailRequestIncomplete(){
        ArrayList<String> list= new ArrayList<>();
        EmailRequest request= new EmailRequest("", "Hello",list,null,
                null, Topic.ALERT,"E-Park","Testing","problem");
        ResponseEntity<EmailResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/notifications/mail",request, EmailResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Request is missing parameters", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        request= new EmailRequest("ff", "",list,null,
                null, Topic.ALERT,"E-Park","","problem");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/notifications/mail",request, EmailResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Request is missing parameters", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void sendMailParticipantsDNE(){
        ArrayList<String> list= new ArrayList<>();
        EmailRequest request= new EmailRequest("EPark Smart Water Monitoring System", "Integration Testing",list,null,
                null, Topic.ALERT,"Joanita","Please tell me you got this email","Integration testing ;)");
        ResponseEntity<EmailResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/notifications/mail",request, EmailResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No recipients specified", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void sendMail(){
        ArrayList<String> list= new ArrayList<>();
        String email = "u19006812@tuks.co.za";
        list.add(email);
        EmailRequest request= new EmailRequest("EPark Smart Water Monitoring System", "Integration Testing",list,null,
                null, Topic.ALERT,"Joanita","Please tell me you got this email 🙏🏻","Integration testing ;)");
        ResponseEntity<EmailResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/notifications/mail",request, EmailResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email sent successfully", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }

    //post: /api/notifications/sms

}
