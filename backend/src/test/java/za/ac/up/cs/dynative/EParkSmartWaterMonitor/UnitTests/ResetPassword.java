package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.ResetPasswordRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.ResetPasswordResponse;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
public class ResetPassword {
    @Mock
    private UserRepo userRepo;

    @Mock(name = "parkService")
    private ParkServiceImpl parkService;

    @Mock(name="NotificationServiceImpl")
    private NotificationServiceImpl notificationService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Attempt to get a reset password code but the user does not exist")
    public void ResetPasswordUserDNE() throws InvalidRequestException {
        //setup
        Mockito.when(userRepo.findUserByUsername("testing")).thenReturn(new ArrayList<>());

        //test
        ResetPasswordRequest request = new ResetPasswordRequest("testing");
        ResetPasswordResponse response = userService.resetPassword(request);
        assertNotNull(response);
        assertEquals("User not found",response.getCode());
    }

    @Test
    @DisplayName("Successfully get the reset password code")
    public void ResetPasswordCodeSuccess() throws InvalidRequestException {
        //setup
        User user = new User();
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepo.findUserByUsername("testing")).thenReturn(users);

        //test
        ResetPasswordRequest request = new ResetPasswordRequest("testing");
        ResetPasswordResponse response = userService.resetPassword(request);
        assertNotNull(response);
        assertEquals(user.getActivationCode(),response.getCode());
    }
}