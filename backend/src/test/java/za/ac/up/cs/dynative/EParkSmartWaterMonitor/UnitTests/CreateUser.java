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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.CreateUserResponse;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
public class CreateUser {
    @Mock
    private UserRepo userRepo;

    @Mock(name = "parkService")
    private ParkServiceImpl parkService;

    @InjectMocks
    private UserServiceImpl userService;

    UUID parkId=UUID.randomUUID();
    String idNumber = "2356897410000";
    String name = "Nita";
    String surname = "Nell";
    String email = "utests@dynative.com";
    String password = "request.getPassword()";
    String username = "chichi";
    String role = "ADMIN";
    String cellNumber = "1234567890";

    @Test
    @DisplayName("Try to create a user but the fields are not complete")
    public void CreateUserIncomplete() throws InvalidRequestException {
        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,"",role,cellNumber);
        CreateUserResponse response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("User's details are incomplete",response.getStatus());
        assertEquals(false,response.getSuccess());

        request= new CreateUserRequest(parkId,"",email,password,name,surname,"",role,cellNumber);
        response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("User's details are incomplete",response.getStatus());
        assertEquals(false,response.getSuccess());

        request= new CreateUserRequest(null,idNumber,email,password,name,surname,username,role,cellNumber);
        response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("User's details are incomplete",response.getStatus());
        assertEquals(false,response.getSuccess());

        request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,"");
        response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("User's details are incomplete",response.getStatus());
        assertEquals(false,response.getSuccess());

        request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,"",cellNumber);
        response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("User's details are incomplete",response.getStatus());
        assertEquals(false,response.getSuccess());

        request= new CreateUserRequest(parkId,idNumber,email,password,name,"",username,role,cellNumber);
        response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("User's details are incomplete",response.getStatus());
        assertEquals(false,response.getSuccess());

        request= new CreateUserRequest(parkId,idNumber,"",password,name,surname,username,role,cellNumber);
        response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("User's details are incomplete",response.getStatus());
        assertEquals(false,response.getSuccess());

        request= new CreateUserRequest(parkId,idNumber,email,password,"",surname,username,role,cellNumber);
        response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("User's details are incomplete",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Try and create an user with an existing Id Number")
    public void createUserDuplicateID() throws InvalidRequestException {
        //setup
        User u= new User();
        List<User> list=new ArrayList<>();
        list.add(u);
        Mockito.when(userRepo.findUserByIdNumber(idNumber)).thenReturn(list);

        //testing
        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,cellNumber);
        CreateUserResponse response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("A user with this id number already exists.",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Try and create an user with an existing username")
    public void createUserDuplicateUsername() throws InvalidRequestException {
        //setup
        User u= new User();
        List<User> list=new ArrayList<>();
        List<User> list2=new ArrayList<>();
        Mockito.when(userRepo.findUserByIdNumber(idNumber)).thenReturn(list);
        list2.add(u);
        Mockito.when(userRepo.findUserByUsername(username)).thenReturn(list2);

        //test
        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,cellNumber);
        CreateUserResponse response=userService.createUser(request);
        assertNotNull(response);
        assertEquals("A user with this username already exists.",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Try and create an user with a park that is non existing")
    public void createUserParkDNE() throws InvalidRequestException {
        //setup
        User u = new User();
        List<User> list = new ArrayList<>();
        List<User> list2 = new ArrayList<>();
        Mockito.when(userRepo.findUserByIdNumber(idNumber)).thenReturn(list);
        Mockito.when(userRepo.findUserByUsername(username)).thenReturn(list2);
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(new FindByParkIdResponse());

        //test
        CreateUserRequest request = new CreateUserRequest(UUID.randomUUID(), idNumber, email, password, name, surname, username, role, cellNumber);
        CreateUserResponse response = userService.createUser(request);
        assertNotNull(response);
        assertEquals("No park with this id exists.", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Create a valid user")
    public void createUser() throws InvalidRequestException {
        //setuo
        List<User> list = new ArrayList<>();
        List<User> list2 = new ArrayList<>();
        Mockito.when(userRepo.findUserByIdNumber(idNumber)).thenReturn(list);
        Mockito.when(userRepo.findUserByUsername(username)).thenReturn(list);
        Park p= new Park("UnitTest",23,11,new HashSet<>(),new HashSet<>());
        FindByParkIdResponse re = new FindByParkIdResponse(true,p);
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(re);

        //test
        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,cellNumber);
        CreateUserResponse response= userService.createUser(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Successfully create user: "+name+ " "+ surname+" and added them to park: UnitTest",response.getStatus());
    }

    @Test
    @DisplayName("Try and create an user but the email is invalid")
    public void CreateUserInvalidEmail() throws InvalidRequestException {
        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,"email",password,name,surname,username,role,cellNumber);
        CreateUserResponse response= userService.createUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("The provided email is not a valid email-address.",response.getStatus());
    }

    @Test
    @DisplayName("Try and create an user but the id is invalid")
    public void CreateUserInvalidId() throws InvalidRequestException {
        CreateUserRequest request= new CreateUserRequest(parkId,"12",email,password,name,surname,username,role,cellNumber);
        CreateUserResponse response= userService.createUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("The provided ID number is not a valid ID number.",response.getStatus());
    }

    @Test
    @DisplayName("Try and create an user but the cellphone is invalid")
    public void CreateUserInvalidCell() throws InvalidRequestException {
        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,"333");
        CreateUserResponse response= userService.createUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Cell-number provided is not valid.",response.getStatus());
    }
}
