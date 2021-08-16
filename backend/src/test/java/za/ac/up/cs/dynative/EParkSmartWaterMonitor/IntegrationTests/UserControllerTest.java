package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.CreateUserResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/user/createUser
    @Test
    public void createUserIncompleteDetails(){
        UUID parkId=UUID.randomUUID();
        String idNumber = "2356897410000";
        String name = "Nita";
        String surname = "Nell";
        String email = "utests@dynative.com";
        String password = "request.getPassword()";
        String username = "chichi";
        String role = "ADMIN";
        String cellNumber = "1234567890";

        //test 1:
        CreateUserRequest request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,"",role,cellNumber);
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("User's details are incomplete",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 2:
        request= new CreateUserRequest(parkId,"",email,password,name,surname,"",role,cellNumber);
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("User's details are incomplete",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 3:
        request= new CreateUserRequest(null,idNumber,email,password,name,surname,username,role,cellNumber);
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("User's details are incomplete",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 4:
        request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,role,"");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("User's details are incomplete",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 5:
        request= new CreateUserRequest(parkId,idNumber,email,password,name,surname,username,"",cellNumber);
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("User's details are incomplete",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 6:
        request= new CreateUserRequest(parkId,idNumber,email,password,name,"",username,role,cellNumber);
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("User's details are incomplete",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 7:
        request= new CreateUserRequest(parkId,idNumber,"",password,name,surname,username,role,cellNumber);
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("User's details are incomplete",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 8:
        request= new CreateUserRequest(parkId,idNumber,email,password,"",surname,username,role,cellNumber);
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("User's details are incomplete",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    //post: /api/user/deleteUser

    //post: /api/user/editUser

    //get: /api/user/getAllUsers

    //post: /api/user/getUser

    //post: /api/user/login

    //post: /api/user/resetPassword
    //post: /api/user/resetPasswordFinalize
}
