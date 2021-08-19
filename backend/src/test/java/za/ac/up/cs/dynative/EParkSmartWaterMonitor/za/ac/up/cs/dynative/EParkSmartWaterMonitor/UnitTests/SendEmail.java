package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.configurations.TwilioConfig;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.models.Topic;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.EmailResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SendEmail {
    @Mock(name = "UserService")
    private UserService userService;

    @Mock
    TwilioConfig config;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    @DisplayName("Send email to a null request")
    public void sensMailRequestNull(){
        EmailResponse response = notificationService.sendMail(null);
        assertNotNull(response);
        assertEquals("Request is null",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Send email but the request is not complete")
    public void sendMailRequestNotComplete(){
        ArrayList<String> list= new ArrayList<>();
        EmailRequest request= new EmailRequest("",
                "Hello",list,null,null, Topic.ALERT,"E-Park","Testing","problem");
        EmailResponse response = notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("Request is missing parameters",response.getStatus());
        assertEquals(false,response.getSuccess());


        request= new EmailRequest("test@gmail.com",
                "",list,null,null, Topic.ALERT,"E-Park","Testing","problem");
        response = notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("Request is missing parameters",response.getStatus());
        assertEquals(false,response.getSuccess());


        request= new EmailRequest("test@gmail.com",
                "Hello",null,null,null, Topic.ALERT,"E-Park","Testing","problem");
        response = notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("Request is missing parameters",response.getStatus());
        assertEquals(false,response.getSuccess());


        request= new EmailRequest("test@gmail.com",
                "Hello",list,null,null, null,"E-Park","Testing","problem");
        response = notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("Request is missing parameters",response.getStatus());
        assertEquals(false,response.getSuccess());


        request= new EmailRequest("test@gmail.com",
                "Hello",list,null,null, Topic.ALERT,"","Testing","problem");
        response = notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("Request is missing parameters",response.getStatus());
        assertEquals(false,response.getSuccess());


        request= new EmailRequest("test@gmail.com",
                "Hello",list,null,null, Topic.ALERT,"E-Park","","problem");
        response = notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("Request is missing parameters",response.getStatus());
        assertEquals(false,response.getSuccess());


        request= new EmailRequest("test@gmail.com",
                "Hello",list,null,null, Topic.ALERT,"E-Park","Testing","");
        response = notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("Request is missing parameters",response.getStatus());
        assertEquals(false,response.getSuccess());


        request= new EmailRequest("test@gmail.com",
                "",list,null,null, null,"E-Park","Testing","problem");
        response = notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("Request is missing parameters",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Send email but no recipients specified")
    public void sensEmailRecipientsNull(){
        ArrayList<String> list= new ArrayList<>();
        EmailRequest request= new EmailRequest("test@email.com",
                "Hello",list,null,null, Topic.ALERT,"E-Park","Testing","problem");
        EmailResponse response=notificationService.sendMail(request);
        assertNotNull(response);
        assertEquals("No recipients specified",response.getStatus());
        assertEquals(false,response.getSuccess());
    }
}
