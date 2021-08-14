package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.EditUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.EditUserResponse;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
public class EditUser {
    @Mock
    private UserRepo userRepo;

    @Mock(name = "parkService")
    private ParkServiceImpl parkService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Try to edit a user but the username does not exist")
    public void EditUserDNE(){
        //setup
        Mockito.when(userRepo.findUserByUsername(Mockito.any())).thenReturn(new ArrayList<>());

        //test
        EditUserRequest request = new EditUserRequest("ch","12","","","","","","");
        EditUserResponse response = userService.editUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("User with that username does not exist.",response.getStatus());
    }

    @Test
    @DisplayName("Try to edit a user but the username is already in use")
    public void EditUserDuplicate(){
        //setup
        List<User> users = new ArrayList<>();
        User u = new User();
        users.add(u);
        Mockito.when(userRepo.findUserByUsername(Mockito.any())).thenReturn(users);
        Mockito.when(userRepo.findUserByUsername(Mockito.any())).thenReturn(users);

        //test
        EditUserRequest request = new EditUserRequest("ch","12","","","","123","","");
        EditUserResponse response = userService.editUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Username is already in use.",response.getStatus());
    }
}
