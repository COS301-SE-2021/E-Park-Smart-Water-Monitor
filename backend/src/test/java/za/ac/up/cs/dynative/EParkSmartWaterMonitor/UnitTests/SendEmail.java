package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.configurations.TwilioConfig;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.models.Topic;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.SMSRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.SMSResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
    public void sensSMSNullRequest(){
        Throwable t= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(null));
        assertEquals("Request is null",t.getMessage());
    }

    @Test
    @DisplayName("Send email but the request is not complete")
    public void sendMailNotComplete(){
        ArrayList<String> list= new ArrayList<>();
        EmailRequest request= new EmailRequest("",
                "Hello",list,null,null, Topic.ALERT,"E-Park","Testing","problem");
        Throwable t= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request));
        assertEquals("Request not complete",t.getMessage());

        EmailRequest request2= new EmailRequest("test@gmail.com",
                "",list,null,null, Topic.ALERT,"E-Park","Testing","problem");
        Throwable t2= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request2));
        assertEquals("Request not complete",t2.getMessage());

        EmailRequest request3= new EmailRequest("test@gmail.com",
                "Hello",null,null,null, Topic.ALERT,"E-Park","Testing","problem");
        Throwable t3= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request3));
        assertEquals("Request not complete",t3.getMessage());

        EmailRequest request4= new EmailRequest("test@gmail.com",
                "Hello",list,null,null, null,"E-Park","Testing","problem");
        Throwable t4= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request4));
        assertEquals("Request not complete",t4.getMessage());

        EmailRequest request5= new EmailRequest("test@gmail.com",
                "Hello",list,null,null, Topic.ALERT,"","Testing","problem");
        Throwable t5= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request5));
        assertEquals("Request not complete",t5.getMessage());

        EmailRequest request6= new EmailRequest("test@gmail.com",
                "Hello",list,null,null, Topic.ALERT,"E-Park","","problem");
        Throwable t6= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request6));
        assertEquals("Request not complete",t6.getMessage());

        EmailRequest request7= new EmailRequest("test@gmail.com",
                "Hello",list,null,null, Topic.ALERT,"E-Park","Testing","");
        Throwable t7= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request7));
        assertEquals("Request not complete",t7.getMessage());

        EmailRequest request8= new EmailRequest("test@gmail.com",
                "",list,null,null, null,"E-Park","Testing","problem");
        Throwable t8= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request8));
        assertEquals("Request not complete",t8.getMessage());
    }

    @Test
    @DisplayName("Send email to no recipients")
    public void sensEmailNullRecipients(){
        ArrayList<String> list= new ArrayList<>();
        EmailRequest request= new EmailRequest("test@email.com",
                "Hello",list,null,null, Topic.ALERT,"E-Park","Testing","problem");
        Throwable t= assertThrows(InvalidRequestException.class,()->notificationService.sendMail(request));
        assertEquals("No recipients specified",t.getMessage());
    }

    @Test
    @DisplayName("Send email from invalid email")
    public void sensSMSInvalidNumber(){
        User u= new User(23456789,"email@test.com","name", "Surname",
                "String password", "uT1", "Agent", new Park(), "123");
        ArrayList<User> list= new ArrayList<>();
        list.add(u);

        SMSRequest request= new SMSRequest(list,"Hi");
        Throwable t= assertThrows(IllegalArgumentException.class,()->notificationService.sendSMS(request));
        assertEquals("The following users have invalid phone numbers: uT1. Please correct and try again.",t.getMessage());
    }
}
