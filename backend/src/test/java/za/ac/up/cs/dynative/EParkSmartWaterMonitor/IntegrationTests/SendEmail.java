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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.models.Topic;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.EmailResponse;

import java.util.ArrayList;

@SpringBootTest(classes = EParkSmartWaterMonitorApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SendEmail {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void sendEmail(){
        ArrayList<String> receivers= new ArrayList<>();
        receivers.add("nita.nell92@gmail.com");
        EmailRequest request = new EmailRequest( "EPark Smart Water Monitoring System",
                "Water1000 Water level alert",receivers,null,null, Topic.ALERT
                ,"WATER145","Please check it out at Site ABC as soon as possible and report your findings.","\"Has critically low water levels.\"");
        ResponseEntity<EmailResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/notifications/mail", request, EmailResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void sendMailUserDNE(){
        ArrayList<String> receivers= new ArrayList<>();
        EmailRequest request = new EmailRequest( "EPark Smart Water Monitoring System",
                "Water1000 Water level alert",receivers,null,null, Topic.ALERT
                ,"WATER145","Please check it out at Site ABC as soon as possible and report your findings.","\"Has critically low water levels.\"");
        ResponseEntity<EmailResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/notifications/mail", request, EmailResponse.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void sendMailFromNotspecified(){
        ArrayList<String> receivers= new ArrayList<>();
        receivers.add("nita.nell92@gmail.com");
        EmailRequest request = new EmailRequest( "",
                "Water1000 Water level alert",receivers,null,null, Topic.ALERT
                ,"WATER145","Please check it out at Site ABC as soon as possible and report your findings.","\"Has critically low water levels.\"");
        ResponseEntity<EmailResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/notifications/mail", request, EmailResponse.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(4)
    public void sendMailTopicNotspecified(){
        ArrayList<String> receivers= new ArrayList<>();
        receivers.add("nita.nell92@gmail.com");
        EmailRequest request = new EmailRequest( "EPark Smart Water Monitoring System",
                "Water1000 Water level alert",receivers,null,null, null
                ,"WATER145","Please check it out at Site ABC as soon as possible and report your findings.","\"Has critically low water levels.\"");
        ResponseEntity<EmailResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/notifications/mail", request, EmailResponse.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

}

