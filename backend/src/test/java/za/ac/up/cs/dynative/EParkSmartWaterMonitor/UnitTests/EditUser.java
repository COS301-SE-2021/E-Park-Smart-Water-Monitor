package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
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

        //test
        EditUserRequest request = new EditUserRequest("ch","12","","","","123","","");
        EditUserResponse response = userService.editUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Username is already in use.",response.getStatus());
    }

    @Test
    @DisplayName("Try to edit a user with invalid data")
    public void EditUserInvalid(){
        //setup
        List<User> users = new ArrayList<>();
        User u = new User();
        users.add(u);
        Mockito.when(userRepo.findUserByUsername("ch")).thenReturn(users);
        Mockito.when(userRepo.findUserByUsername("123")).thenReturn(new ArrayList<>());
        Mockito.when(userRepo.findUserByEmail(Mockito.any())).thenReturn(new ArrayList<>());

        //test invalid cellphone
        EditUserRequest request = new EditUserRequest("ch","12","","","","123","","77");
        EditUserResponse response = userService.editUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Cell-number provided is not valid.",response.getStatus());

        //test invalid email
        request = new EditUserRequest("ch","12","55","","","123","","0728480427");
        response = userService.editUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("The provided email is not a valid email-address.",response.getStatus());

        //test invalid id
        request = new EditUserRequest("ch","12","nita.nell92@gmail.com","","","123","","0728480427");
        response = userService.editUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("The provided ID number is not a valid ID number.",response.getStatus());
    }

    @Test
    @DisplayName("Try to edit a user with an existing email")
    public void EditUserDuplicateEmail() {
        //setup
        List<User> users = new ArrayList<>();
        User u = new User();
        users.add(u);
        Mockito.when(userRepo.findUserByUsername("ch")).thenReturn(users);
        Mockito.when(userRepo.findUserByUsername("123")).thenReturn(new ArrayList<>());
        Mockito.when(userRepo.findUserByEmail(Mockito.any())).thenReturn(users);

        //test
        EditUserRequest request = new EditUserRequest("ch", "12", "nita.nell92@gmail.com", "", "", "123", "", "0728480427");
        EditUserResponse response = userService.editUser(request);
        assertNotNull(response);
        assertEquals(false, response.getSuccess());
        assertEquals("The provided email is already in use.", response.getStatus());
    }

    @Test
    @DisplayName("Try to edit an user and succeeds")
    public void EditUserSuccess() {
        //setup
        List<User> users = new ArrayList<>();
        User u = new User();
        users.add(u);
        Mockito.when(userRepo.findUserByUsername("ch")).thenReturn(users);
        Mockito.when(userRepo.findUserByUsername("123")).thenReturn(new ArrayList<>());
        Mockito.when(userRepo.findUserByEmail(Mockito.any())).thenReturn(new ArrayList<>());

        //test
        EditUserRequest request = new EditUserRequest("ch", "1299999999999", "nita.nell92@gmail.com", "Mieke", "kk", "123", "Engineer", "0728480427");
        EditUserResponse response = userService.editUser(request);
        assertNotNull(response);
        assertEquals(true, response.getSuccess());
        assertEquals("User details updated.", response.getStatus());
    }
}
