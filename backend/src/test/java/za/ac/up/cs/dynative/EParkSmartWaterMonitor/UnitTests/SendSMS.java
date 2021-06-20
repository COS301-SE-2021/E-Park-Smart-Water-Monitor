package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import com.twilio.Twilio;
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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.SMSRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.SMSResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SendSMS {

    @Mock(name = "UserService")
    private UserService userService;

    @Mock
    TwilioConfig config;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    @DisplayName("Send SMS to a null request")
    public void sensSMSNullRequest(){
        Throwable t= assertThrows(InvalidRequestException.class,()->notificationService.sendSMS(null));
        assertEquals("Request is null",t.getMessage());
    }

    @Test
    @DisplayName("Send SMS to no recipients")
    public void sensSMSNullRecipients(){
        Throwable t= assertThrows(InvalidRequestException.class,()->notificationService.sendSMS(new SMSRequest(new ArrayList<User>(),"hi")));
        assertEquals("No recipients specified",t.getMessage());
    }

    @Test
    @DisplayName("Send SMS to invalid numbers")
    public void sensSMSInvalidNumber(){
        User u= new User(23456789,"email@test.com","name", "Surname",
                "String password", "uT1", "Agent", new Park(), "123");
        ArrayList<User> list= new ArrayList<>();
        list.add(u);

        SMSRequest request= new SMSRequest(list,"Hi");
        Throwable t= assertThrows(IllegalArgumentException.class,()->notificationService.sendSMS(request));
        assertEquals("The following users have invalid phone numbers: uT1. Please correct and try again.",t.getMessage());
    }

    /*@Test
    @DisplayName("send a sms")
    public void sendSMS() throws InvalidRequestException {
        User u= new User(23456789,"email@test.com","name", "Surname",
                "String password", "uT1", "Agent", new Park(), "0832920868");
        ArrayList<User> list= new ArrayList<>();
        list.add(u);

        Mockito.when(config.getNumber()).then(Twilio.init()).thenReturn("1234567890");

        SMSRequest request= new SMSRequest(list,"Hi");
        SMSResponse response= notificationService.sendSMS(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Messages sent successfully",response.getStatus());
    }*/

}
