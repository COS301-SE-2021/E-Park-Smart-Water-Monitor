package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.configurations.TwilioConfig;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.SMSRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.SMSResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SendSMS {

    @Mock(name = "UserService")
    private UserService userService;

    @Mock
    TwilioConfig config;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    @DisplayName("Attempt to send a sms but the request is null")
    public void sensSMSRequestNull(){
        SMSResponse response = notificationService.sendSMS(null);
        assertNotNull(response);
        assertEquals("Request is null", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Attempt to send an sms but there are no recipients")
    public void sensSMSNoRecipients(){
        SMSResponse response =notificationService.sendSMS(new SMSRequest(new ArrayList<UUID>(),"hi"));
        assertNotNull(response);
        assertEquals("No recipients specified", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Send SMS to invalid numbers")
    public void sensSMSInvalidNumber(){
        //setup
        User u= new User(23456789,"email@test.com","name", "Surname",
                "String password", "uT1", "Agent", new Park(), "123");
        ArrayList<User> list= new ArrayList<>();
        list.add(u);

        //test
        SMSRequest request= new SMSRequest(list,"Hi");
        SMSResponse response = notificationService.sendSMS(request);
        assertNotNull(response);
        assertEquals("The following users have invalid phone numbers: uT1. Please correct and try again.", response.getStatus());
        assertEquals(false, response.getSuccess());
    }


}
