package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.LoginRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.LoginResponse;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class Login {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    String username="UnitTest01";
    String password="HopeThisRuns";

    @Test
    @DisplayName("Try to login but details are not complete")
    public void loginIncompleteDetails(){
        //test 1
        LoginRequest request= new LoginRequest(username,"");
        LoginResponse response = userService.loginUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("",response.getJwt());

        //test 2
        request= new LoginRequest("",password);
        response = userService.loginUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("",response.getJwt());

        //test 3
        request= new LoginRequest("","");
        response = userService.loginUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("",response.getJwt());
    }

    @Test
    @DisplayName("Attempt to login but the user does not exist")
    public void LoginUserDNE(){
        //setup
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        Mockito.when(userRepo.findUserByUsername("unitTest22")).thenReturn(new ArrayList<>());

        //test
        String passw= "t8#tkapb6ssc1|3?5*3/";
        LoginRequest request= new LoginRequest("unitTest22",passw);
        LoginResponse response = userService.loginUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("",response.getJwt());
    }

    @Test
    @DisplayName("Try to login but password is not correct")
    public void loginIncorrectPassword(){
        //setup
        List<User> userList=new ArrayList<>();
        Park park= new Park();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        User user= new User(1234567890,"test@gmail.com","Unit","Surname",passwordEncoder.encode("t8#tkapb6ssc1|3?5*3/")
                ,"unitTest22","RANGER",park,"1234567890");
        userList.add(user);
        Mockito.when(userRepo.findUserByUsername("unitTest22")).thenReturn(userList);

        //test
        String pass= "t8#tkalb6ssc1|3?7*3/";
        LoginRequest request= new LoginRequest("unitTest22",pass);
        LoginResponse response = userService.loginUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("",response.getJwt());
    }

    @Test
    @DisplayName("Try to login but username is not correct")
    public void loginIncorrectUserDNE(){
        //setup
        List<User> userList=new ArrayList<>();
        Park park= new Park();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        User user= new User(1234567890,"test@gmail.com","Unit","Surname",passwordEncoder.encode("t8#tkapb6ssc1|3?5*3/")
                ,"unitTest22","RANGER",park,"1234567890");
        userList.add(user);
        Mockito.when(userRepo.findUserByUsername("unitTest2")).thenReturn(new ArrayList<>());

        //test
        LoginRequest request= new LoginRequest("unitTest2","t8#tkapb6ssc1|3?5*3/");
        LoginResponse response = userService.loginUser(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
    }
}
