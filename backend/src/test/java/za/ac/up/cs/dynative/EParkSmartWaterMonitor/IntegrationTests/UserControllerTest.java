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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.DeleteUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.EditUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.FindUserByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.*;

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

    @Test
    public void createUserDuplicate(){
        CreateUserRequest request= new CreateUserRequest(UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80"),"9871233577124","nita.nell92@gmail.com","dynative","IntTesting123123","surname","ChiChiTestingADMIN","ADMIN","0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("A user with this username already exists.",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void createUserInvalid(){
        //test 1:
        CreateUserRequest request= new CreateUserRequest(UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80"),"98712337124","nita.nell92@gmail.com","dynative","IntTesting123123","surname","ChiChiTestingADMIN","ADMIN","0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("The provided ID number is not a valid ID number.",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 2:
        request= new CreateUserRequest(UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80"),"9871233577124","nita.nell92gmail.com","dynative","IntTesting123123","surname","ChiChiTestingADMIN","ADMIN","0728480427");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("The provided email is not a valid email-address.",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());

        //test 3:
        request= new CreateUserRequest(UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80"),"9871233577124","nita.nell92@gmail.com","dynative","IntTesting123123","surname","ChiChiTestingADMIN","ADMIN","08480427");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN","dynativeNext")
                .postForEntity("/api/user/createUser", request,CreateUserResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Cell-number provided is not valid.",response.getBody().getStatus());
        assertEquals(false,response.getBody().getSuccess());
    }

    @Test
    public void createUserParkDNE() {
        CreateUserRequest request = new CreateUserRequest(UUID.fromString("4c0a1f95-551b-4885-b3fe-5d27c71ebd80"), "9871233577124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", "IntTesting123123", "ADMIN", "0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No park with this id exists.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    //do not run this with createDeleteUser()
    /*@Test
    public void createUser() {
        CreateUserRequest request = new CreateUserRequest(UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80"), "9871233577124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", "IntTesting123123", "ADMIN", "0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully create user: IntTesting123123 surname and added them to park: Kruger National Park", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }*/

    //post: /api/user/deleteUser
    @Test
    public void createDeleteUser() {
        CreateUserRequest request = new CreateUserRequest(UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80"), "9871233577124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", "IntTesting123123", "ADMIN", "0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully create user: IntTesting123123 surname and added them to park: Kruger National Park", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());

        DeleteUserRequest requestD = new DeleteUserRequest(response.getBody().getId());
        ResponseEntity<DeleteUserResponse> responseD = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/deleteUser", requestD, DeleteUserResponse.class);
        assertEquals(HttpStatus.OK, responseD.getStatusCode());
        assertEquals("Sucessfully deleteD user: IntTesting123123 surname", responseD.getBody().getStatus());
        assertEquals(true, responseD.getBody().getSuccess());
    }

    //do not run this with createDeleteUser()
    /*@Test
    public void deleteUser() {
        DeleteUserRequest request = new DeleteUserRequest(UUID.fromString("cbaa58eb-4e9b-44ec-8837-2ffde8604c5a"));
        ResponseEntity<DeleteUserResponse> responseD = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/deleteUser", request, DeleteUserResponse.class);
        assertEquals(HttpStatus.OK, responseD.getStatusCode());
        assertEquals("Sucessfully deleteD user: IntTesting123123 surname", responseD.getBody().getStatus());
        assertEquals(true, responseD.getBody().getSuccess());
    }*/

    @Test
    public void deleteUserIdNull(){
        DeleteUserRequest requestD = new DeleteUserRequest(null);
        ResponseEntity<DeleteUserResponse> responseD = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/deleteUser", requestD, DeleteUserResponse.class);
        assertEquals(HttpStatus.OK, responseD.getStatusCode());
        assertEquals("Failed to delete user no id specified!", responseD.getBody().getStatus());
        assertEquals(false, responseD.getBody().getSuccess());
    }

    @Test
    public void deleteUserDNE(){
        DeleteUserRequest requestD = new DeleteUserRequest(UUID.randomUUID());
        ResponseEntity<DeleteUserResponse> responseD = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/deleteUser", requestD, DeleteUserResponse.class);
        assertEquals(HttpStatus.OK, responseD.getStatusCode());
        assertEquals("Failed to delete user: No user with this id exists!", responseD.getBody().getStatus());
        assertEquals(false, responseD.getBody().getSuccess());
    }

    //post: /api/user/editUser
    @Test
    public void editUserDNE(){
        EditUserRequest request = new EditUserRequest("ch","12","","","","","","");
        ResponseEntity<EditUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser",request,EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User with that username does not exist.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void editUserDup(){
        EditUserRequest request = new EditUserRequest("Michaela","12","","","","ChiChiTestingADMIN","","");
        ResponseEntity<EditUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser",request,EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Username is already in use.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void editUserInvalid(){
        //test 1
        EditUserRequest request = new EditUserRequest("Michaela","12","","","","Michaela.com","","33");
        ResponseEntity<EditUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser",request,EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cell-number provided is not valid.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 2
        request = new EditUserRequest("Michaela","12","rolanstrydom@icloud.com","","","Michaela.com","","0797310173");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser",request,EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The provided email is already in use.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 3
        request = new EditUserRequest("Michaela","12","rolanstrydom@icloud.com","","","Michaela.com","","0797310173");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser",request,EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The provided email is already in use.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 4
        request = new EditUserRequest("Michaela","12","rolanstrydcloud.com","","","Michaela.com","","0797310173");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser",request,EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The provided email is not a valid email-address.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 5
        request = new EditUserRequest("Michaela","12","","","","Michaela.com","","");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser",request,EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The provided ID number is not a valid ID number.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void editUserSuccess() {
        //test 1
        EditUserRequest request = new EditUserRequest("Michaela", "", "", "", "", "Michaela.com", "", "");
        ResponseEntity<EditUserResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User details updated.", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());

        //test 2
        request = new EditUserRequest("Michaela.com", "9877132522123", "", "", "", "Michaela", "", "");
        response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User details updated.", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }

    //post: /api/user/getUser
    @Test
    public void getUserDNE() {
        FindUserByIdRequest request = new FindUserByIdRequest(null);
        ResponseEntity<FindUserByIdResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/getUser", request, FindUserByIdResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody().getUser());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void getUserSuccess() {
        FindUserByIdRequest request = new FindUserByIdRequest(UUID.fromString("6716af93-1563-42b0-b1b9-5373cd19c0b4"));
        ResponseEntity<FindUserByIdResponse> response = restTemplate.withBasicAuth("ChiChiTestingADMIN", "dynativeNext")
                .postForEntity("/api/user/getUser", request, FindUserByIdResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("REEE.com", response.getBody().getUser().getName());
        assertEquals("rolanstrydom@icloud.com", response.getBody().getUser().getEmail());
        assertEquals("Michaela", response.getBody().getUser().getUsername());
        assertEquals(true, response.getBody().getSuccess());
    }

    //post: /api/user/login

    //post: /api/user/resetPassword
    //post: /api/user/resetPasswordFinalize
}
