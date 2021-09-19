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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.ResetPasswordFinalizeRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.ResetPasswordRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.ResetPasswordFinalizeResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.ResetPasswordResponse;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordFinalize {
    @Mock
    private UserRepo userRepo;

    @Mock(name = "parkService")
    private ParkServiceImpl parkService;

    @Mock(name="NotificationServiceImpl")
    private NotificationServiceImpl notificationService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Attempt to reset the password but the user does not exist")
    public void ResetPasswordFinalizeUserDNE(){
        //setup
        Mockito.when(userRepo.findUserByUsername("testing")).thenReturn(new ArrayList<>());

        //test
        ResetPasswordFinalizeRequest request = new ResetPasswordFinalizeRequest("testing","","","");
        ResetPasswordFinalizeResponse response = userService.resetPasswordFinalize(request);
        assertNotNull(response);
        assertEquals("User not found",response.getMessage());
        assertEquals(false,response.isSuccess());
    }

    @Test
    @DisplayName("User code is incorrect, could not rest the password")
    public void ResetPasswordFinalizeWrongCode() throws InvalidRequestException {
        //setup
        User user = new User();
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepo.findUserByUsername("testing")).thenReturn(users);
        ResetPasswordRequest request = new ResetPasswordRequest("testing");
        ResetPasswordResponse response = userService.resetPassword(request);
        assertNotNull(response);
        assertEquals(user.getActivationCode(),response.getCode());
        user.setResetPasswordExpiration(LocalDateTime.now().plusHours(3));

        //test
        ResetPasswordFinalizeRequest requestFinal = new ResetPasswordFinalizeRequest("testing","abc","123","123");
        ResetPasswordFinalizeResponse responseFinal = userService.resetPasswordFinalize(requestFinal);
        assertNotNull(response);
        assertEquals("There seems to be a typo in the code or provided password",responseFinal.getMessage());
        assertEquals(false,responseFinal.isSuccess());
    }

    @Test
    @DisplayName("User code is expired, could not rest the password")
    public void ResetPasswordFinalizeCodeExpired() throws InvalidRequestException {
        //setup
        User user = new User();
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepo.findUserByUsername("testing")).thenReturn(users);
        ResetPasswordRequest request = new ResetPasswordRequest("testing");
        ResetPasswordResponse response = userService.resetPassword(request);
        assertNotNull(response);
        assertEquals(user.getActivationCode(),response.getCode());
        user.setResetPasswordExpiration(LocalDateTime.now());

        //test
        ResetPasswordFinalizeRequest requestFinal = new ResetPasswordFinalizeRequest("testing","te#t8apb6ksc1|3?5*3/","te#t8apb6ksc1|3?5*3/","123");
        ResetPasswordFinalizeResponse responseFinal = userService.resetPasswordFinalize(requestFinal);
        assertNotNull(response);
        assertEquals("Password reset failed, code expired",responseFinal.getMessage());
        assertEquals(false,responseFinal.isSuccess());
    }

    @Test
    @DisplayName("Successfully reset the password")
    public void ResetPasswordSuccess() throws InvalidRequestException {
        //setup
        User user = new User();
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepo.findUserByUsername("testing")).thenReturn(users);
        ResetPasswordRequest request = new ResetPasswordRequest("testing");
        ResetPasswordResponse response = userService.resetPassword(request);
        assertNotNull(response);
        assertEquals(user.getActivationCode(),response.getCode());
        user.setResetPasswordExpiration(LocalDateTime.now().plusHours(1));

        //test
        ResetPasswordFinalizeRequest requestFinal = new ResetPasswordFinalizeRequest("testing", user.getActivationCode(), "te#T%666t8apb6ksc1|3?5*3/","te#T%666t8apb6ksc1|3?5*3/");
        ResetPasswordFinalizeResponse responseFinal = userService.resetPasswordFinalize(requestFinal);
        assertNotNull(response);
        assertEquals("Password successfully changed",responseFinal.getMessage());
        assertEquals(true,responseFinal.isSuccess());
    }

    @Test
    @DisplayName("Password does not match the requirements")
    public void ResetPasswordRegexFail() throws InvalidRequestException {
        //setup
        User user = new User();
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepo.findUserByUsername("testing")).thenReturn(users);
        ResetPasswordRequest request = new ResetPasswordRequest("testing");
        ResetPasswordResponse response = userService.resetPassword(request);
        assertNotNull(response);
        assertEquals(user.getActivationCode(),response.getCode());
        user.setResetPasswordExpiration(LocalDateTime.now().plusHours(1));

        //test
        ResetPasswordFinalizeRequest requestFinal = new ResetPasswordFinalizeRequest("testing", user.getActivationCode(), "te#t8apb6ksc1|3?5*3/","te#t8apb6ksc1|3?5*3/");
        ResetPasswordFinalizeResponse responseFinal = userService.resetPasswordFinalize(requestFinal);
        assertNotNull(response);
        assertEquals("The password does not comply with the minimum requirements. Your password should contain" +
                " atleast 10 characters, one capital letter" +
                ", one lower case letter, one number and finally a minimum of one " +
                "special character: #, ?, !, @, $, %, ^, &, *, -",responseFinal.getMessage());
        assertEquals(false,responseFinal.isSuccess());
    }
}