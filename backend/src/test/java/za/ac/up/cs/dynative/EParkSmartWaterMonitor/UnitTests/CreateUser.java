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
    String idNumber = "2356897410";
    String name = "Nita";
    String surname = "Nell";
    String email = "utests@dynative.com";
    String password = "request.getPassword()";
    String username = "chichi";
    String role = "ADMIN";
    String cellNumber = "1234567890";

    @Test
    @DisplayName("Try to create a user but the fields are not complete")
    public void CreateUserIncomplete(){
        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,"",role,cellNumber);
        Throwable t= assertThrows(InvalidRequestException.class,()->userService.createUser(request));
        assertEquals("Details incomplete",t.getMessage());

        CreateUserRequest request2= new CreateUserRequest(parkId,"",email,password,name,surname,"",role,cellNumber);
        Throwable t2= assertThrows(InvalidRequestException.class,()->userService.createUser(request2));
        assertEquals("Details incomplete",t2.getMessage());

        CreateUserRequest request3= new CreateUserRequest(null,idNumber,email,password,name,surname,username,role,cellNumber);
        Throwable t3= assertThrows(InvalidRequestException.class,()->userService.createUser(request3));
        assertEquals("Details incomplete",t3.getMessage());

        CreateUserRequest request4= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,"");
        Throwable t4= assertThrows(InvalidRequestException.class,()->userService.createUser(request));
        assertEquals("Details incomplete",t4.getMessage());

        CreateUserRequest request5= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,"",cellNumber);
        Throwable t5= assertThrows(InvalidRequestException.class,()->userService.createUser(request5));
        assertEquals("Details incomplete",t5.getMessage());

        CreateUserRequest request6= new CreateUserRequest(parkId,idNumber,email,password,name,"",username,role,cellNumber);
        Throwable t6= assertThrows(InvalidRequestException.class,()->userService.createUser(request6));
        assertEquals("Details incomplete",t6.getMessage());

        CreateUserRequest request7= new CreateUserRequest(parkId,idNumber,"",password,name,surname,username,role,cellNumber);
        Throwable t7= assertThrows(InvalidRequestException.class,()->userService.createUser(request7));
        assertEquals("Details incomplete",t7.getMessage());

        CreateUserRequest request9= new CreateUserRequest(parkId,idNumber,email,password,"",surname,username,role,cellNumber);
        Throwable t9= assertThrows(InvalidRequestException.class,()->userService.createUser(request9));
        assertEquals("Details incomplete",t9.getMessage());

        CreateUserRequest request8= new CreateUserRequest(parkId,idNumber,email,"",name,surname,username,role,cellNumber);
        Throwable t8= assertThrows(InvalidRequestException.class,()->userService.createUser(request8));
        assertEquals("Details incomplete",t8.getMessage());
    }

    @Test
    @DisplayName("Try and create an user with an existing Id Number")
    public void createUserDupID(){
        User u= new User();
        List<User> list=new ArrayList<>();
        list.add(u);
        Mockito.when(userRepo.findUserByIdNumber(idNumber)).thenReturn(list);

        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,cellNumber);
        Throwable t= assertThrows(InvalidRequestException.class,()->userService.createUser(request));
        assertEquals("A user with this id number already exists.",t.getMessage());

    }

    @Test
    @DisplayName("Try and create an user with an existing username")
    public void createUserDupUsername(){
        User u= new User();
        List<User> list=new ArrayList<>();
        List<User> list2=new ArrayList<>();
        Mockito.when(userRepo.findUserByIdNumber(idNumber)).thenReturn(list);
        list2.add(u);
        Mockito.when(userRepo.findUserByUsername(username)).thenReturn(list2);

        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,cellNumber);
        Throwable t= assertThrows(InvalidRequestException.class,()->userService.createUser(request));
        assertEquals("A user with this username already exists.",t.getMessage());
    }


    @Test
    @DisplayName("Create a valid user")
    public void createUser() throws InvalidRequestException {
        List<User> list = new ArrayList<>();
        List<User> list2 = new ArrayList<>();
        Mockito.when(userRepo.findUserByIdNumber(idNumber)).thenReturn(list);
        Mockito.when(userRepo.findUserByUsername(username)).thenReturn(list);
        Park p= new Park("UnitTest",23,11,new HashSet<>(),new HashSet<>());
        FindByParkIdResponse re = new FindByParkIdResponse(true,p);
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(re);

        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,cellNumber);
        CreateUserResponse response= userService.createUser(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Successfully create user: "+name+ " "+ surname+" and added them to park: UnitTest",response.getStatus());
    }
}
