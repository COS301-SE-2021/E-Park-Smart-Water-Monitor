package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.*;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Value("${app.userName1}")
    private String userName1;
    @Value("${app.userP1}")
    private String userPassword1;
    @Value("${app.parkID}")
    private String parkID;
    @Value("${app.userName3}")
    private String userName3;
    @Value("${app.userID3}")
    private String userID3;
    @Value("${app.userName2}")
    private String userName2;
    @Value("${app.userP2}")
    private String userPassword2;
    @Value("${app.userResponseName}")
    private String responseName;
    @Value("${app.userResponseEmail}")
    private String responseEmail;
    @Value("${app.userResponseUserName}")
    private String responseUserName;
    @Value("${app.user1SaltedP}")
    private String passSalted1;
    @Value("${app.user2SaltedP}")
    private String passSalted2;

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/user/createUser
    @Test
    @Order(1)
    public void createUserIncompleteDetails() {
        UUID parkId = UUID.randomUUID();
        String idNumber = "2356897410000";
        String name = "Nita";
        String surname = "Nell";
        String email = "utests@dynative.com";
        String password = "request.getPassword()";
        String username = "chichiii";
        String role = "ADMIN";
        String cellNumber = "1234567890";

        //test 1:
        CreateUserRequest request = new CreateUserRequest(parkId, idNumber, email, password, name, surname, "", role, cellNumber);
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's details are incomplete", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 2:
        request = new CreateUserRequest(parkId, "", email, password, name, surname, "", role, cellNumber);
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's details are incomplete", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 3:
        request = new CreateUserRequest(null, idNumber, email, password, name, surname, username, role, cellNumber);
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's details are incomplete", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 4:
        request = new CreateUserRequest(parkId, idNumber, email, password, name, surname, username, role, "");
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's details are incomplete", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 5:
        request = new CreateUserRequest(parkId, idNumber, email, password, name, surname, username, "", cellNumber);
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's details are incomplete", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 6:
        request = new CreateUserRequest(parkId, idNumber, email, password, name, "", username, role, cellNumber);
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's details are incomplete", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 7:
        request = new CreateUserRequest(parkId, idNumber, "", password, name, surname, username, role, cellNumber);
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's details are incomplete", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 8:
        request = new CreateUserRequest(parkId, idNumber, email, password, "", surname, username, role, cellNumber);
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User's details are incomplete", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    @Order(2)
    public void createUserDuplicate() {
        CreateUserRequest request = new CreateUserRequest(UUID.fromString(parkID), "9871233577124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", userName3, "ADMIN", "0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("A user with this username already exists.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    @Order(3)
    public void createUserInvalid() {
        //test 1:
        CreateUserRequest request = new CreateUserRequest(UUID.fromString(parkID), "98712337124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", userName3, "ADMIN", "0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The provided ID number is not a valid ID number.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 2:
        request = new CreateUserRequest(UUID.fromString(parkID), "9871233577124", "nita.nell92gmail.com", "dynative", "IntTesting123123", "surname", userName3, "ADMIN", "0728480427");
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The provided email is not a valid email-address.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());

        //test 3:
        request = new CreateUserRequest(UUID.fromString(parkID), "9871233577124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", "ChiChiTestingADMIN", "ADMIN", "08480427");
        response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cell-number provided is not valid.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    @Order(4)
    public void createUserParkDNE() {
        CreateUserRequest request = new CreateUserRequest(UUID.randomUUID(), "9871233577124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", "IntTestingkjflkejrfl123123", "ADMIN", "0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No park with this id exists.", response.getBody().getStatus());
        assertEquals(false, response.getBody().getSuccess());
    }

    //do not run this with createDeleteUser()
    /*@Test
    public void createUser() {
        CreateUserRequest request = new CreateUserRequest(UUID.fromString("4c0a1f95-051b-4885-b3fe-5d27c71ebd80"), "9871233577124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", "IntTesting123123", "ADMIN", "0728480427");
        ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully create user: IntTesting123123 surname and added them to park: Kruger National Park", response.getBody().getStatus());
        assertEquals(true, response.getBody().getSuccess());
    }*/

    //post: /api/user/deleteUser
//     @Test
//     @Order(5)
//     public void createDeleteUser() {
//         CreateUserRequest request = new CreateUserRequest(UUID.fromString(parkID), "9871233577124", "nita.nell92@gmail.com", "dynative", "IntTesting123123", "surname", "IntTesting123123", "ADMIN", "0728480427");
//         ResponseEntity<CreateUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
//                 .postForEntity("/api/user/createUser", request, CreateUserResponse.class);
//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         assertEquals("Successfully create user: IntTesting123123 surname and added them to park: Rietvlei Nature Reserve", response.getBody().getStatus());
//         assertEquals(true, response.getBody().getSuccess());


//         DeleteUserRequest requestD = new DeleteUserRequest(response.getBody().getId());
//         ResponseEntity<DeleteUserResponse> responseD = restTemplate.withBasicAuth(userName1, userPassword1)
//                 .postForEntity("/api/user/deleteInternal", requestD, DeleteUserResponse.class);
//         assertEquals(HttpStatus.OK, responseD.getStatusCode());
//         assertEquals("Sucessfully deleteD user: IntTesting123123 surname", responseD.getBody().getStatus());
//         assertEquals(true, responseD.getBody().getSuccess());
//     }

    //do not run this with createDeleteUser()
    /*@Test
    public void deleteUser() {
        DeleteUserRequest request = new DeleteUserRequest(UUID.fromString("cbaa58eb-4e9b-44ec-8837-2ffde8604c5a"));
        ResponseEntity<DeleteUserResponse> responseD = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/deleteUser", request, DeleteUserResponse.class);
        assertEquals(HttpStatus.OK, responseD.getStatusCode());
        assertEquals("Sucessfully deleteD user: IntTesting123123 surname", responseD.getBody().getStatus());
        assertEquals(true, responseD.getBody().getSuccess());
    }*/

//    @Test
//    @Order(6)
//    public void deleteUserIdNull() {
//        DeleteUserRequest requestD = new DeleteUserRequest(null);
//        ResponseEntity<DeleteUserResponse> responseD = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/deleteUser", requestD, DeleteUserResponse.class);
//        assertEquals(HttpStatus.OK, responseD.getStatusCode());
//        assertEquals("Failed to delete user no id specified!", responseD.getBody().getStatus());
//        assertEquals(false, responseD.getBody().getSuccess());
//    }
//
//    @Test
//    @Order(7)
//    public void deleteUserDNE() {
//        DeleteUserRequest requestD = new DeleteUserRequest(UUID.randomUUID());
//        ResponseEntity<DeleteUserResponse> responseD = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/deleteUser", requestD, DeleteUserResponse.class);
//        assertEquals(HttpStatus.OK, responseD.getStatusCode());
//        assertEquals("Failed to delete user: No user with this id exists!", responseD.getBody().getStatus());
//        assertEquals(false, responseD.getBody().getSuccess());
//    }

    //post: /api/user/editUser
    @Test
    @Order(8)
    public void editUserDNE() {
//        EditUserRequest request = new EditUserRequest("ch", "12", "", "", "", "", "", "");
//        ResponseEntity<EditUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("User with that username does not exist.", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    @Order(9)
    public void editUserDup() {
//        EditUserRequest request = new EditUserRequest("userName3", "12", "", "", "", "testingTwo", "", "");
//        ResponseEntity<EditUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Username is already in use.", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    @Order(10)
    public void editUserInvalid() {
//        //test 1
//        EditUserRequest request = new EditUserRequest(userName2, "12", "", "", "", "Michaela.com", "", "33");
//        ResponseEntity<EditUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Cell-number provided is not valid.", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
//
//        //test 2
//        request = new EditUserRequest(userName2, "12", "dewom@icloud.com", "", "", "Michaela.com", "", "0797310173");
//        response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("The provided email is already in use.", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
//
//        //test 3
//        request = new EditUserRequest(userName2, "12", "rolanydom@icloud.com", "", "", "Michaela.com", "", "0797310173");
//        response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("The provided email is already in use.", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
//
//        //test 4
//        request = new EditUserRequest(userName2, "12", "rolanstrydcloud.com", "", "", "Michaela.com", "", "0797310173");
//        response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("The provided email is not a valid email-address.", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
//
//        //test 5
//        request = new EditUserRequest(userName2, "12", "", "", "", "Michaela.com", "", "");
//        response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("The provided ID number is not a valid ID number.", response.getBody().getStatus());
//        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    @Order(11)
    public void editUserSuccess() {
        //test 1
//        EditUserRequest request = new EditUserRequest(userName2", "", "", "", "", "testingTwo2", "", "");
//        ResponseEntity<EditUserResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("User details updated.", response.getBody().getStatus());
//        assertEquals(true, response.getBody().getSuccess());
//
//        //test 2
//        request = new EditUserRequest("testingTwo2", "9877132522123", "", "", "", userName2", "", "");
//        response = restTemplate.withBasicAuth(userName1, userPassword1)
//                .postForEntity("/api/user/editUser", request, EditUserResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("User details updated.", response.getBody().getStatus());
//        assertEquals(true, response.getBody().getSuccess());
    }

    //post: /api/user/getUser
    @Test
    @Order(12)
    public void getUserDNE() {
        FindUserByIdRequest request = new FindUserByIdRequest(null);
        ResponseEntity<FindUserByIdResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/getUser", request, FindUserByIdResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody().getUser());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    @Order(13)
    public void getUserSuccess() {
        FindUserByIdRequest request = new FindUserByIdRequest(UUID.fromString(userID3));
        ResponseEntity<FindUserByIdResponse> response = restTemplate.withBasicAuth(userName1, userPassword1)
                .postForEntity("/api/user/getUser", request, FindUserByIdResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseName, response.getBody().getUser().getName());
        assertEquals(responseEmail, response.getBody().getUser().getEmail());
        assertEquals(responseUserName, response.getBody().getUser().getUsername());
        assertEquals(true, response.getBody().getSuccess());
    }

    //post: /api/user/login
    @Test
    @Order(14)
    public void loginIncomplete() {
        //test 1
        LoginRequest request = new LoginRequest("", "");
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity("/api/user/login", request, LoginResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().getSuccess());
        assertEquals("", response.getBody().getJwt());

        //test 2
        request = new LoginRequest("hello", "");
        response = restTemplate.postForEntity("/api/user/login", request, LoginResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().getSuccess());
        assertEquals("", response.getBody().getJwt());

        //test 3
        request = new LoginRequest("", "dd");
        response = restTemplate.postForEntity("/api/user/login", request, LoginResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().getSuccess());
        assertEquals("", response.getBody().getJwt());
    }

    @Test
    @Order(15)
    public void LoginUserDNE() {
//        LoginRequest request = new LoginRequest("hello", "1");
//        ResponseEntity<LoginResponse> response = restTemplate.postForEntity("/api/user/login", request, LoginResponse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(false, response.getBody().getSuccess());
//        assertEquals("", response.getBody().getJwt());
    }

    @Test
    @Order(16)
    public void LoginUserWrongPassword() {
        LoginRequest request = new LoginRequest(userName1, passSalted2);
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity("/api/user/login", request, LoginResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().getSuccess());
        assertEquals("", response.getBody().getJwt());
    }

    @Test
    @Order(17)
    public void LoginSuccess() {
        LoginRequest request = new LoginRequest(userName1, passSalted1);
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity("/api/user/login", request, LoginResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().getSuccess());
        assertNotEquals("", response.getBody().getJwt());
    }

    //post: /api/user/resetPassword
    @Test
    @Order(18)
    public void resetPasswordUserDNE() {
        ResetPasswordRequest request = new ResetPasswordRequest("hello");
        ResponseEntity<ResetPasswordResponse> response = restTemplate.postForEntity("/api/user/resetPassword", request, ResetPasswordResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User not found", response.getBody().getCode());
    }

    @Test
    @Order(19)
    public void resetPasswordGetCodeSuccess() {
        ResetPasswordRequest request = new ResetPasswordRequest(userName2);
        ResponseEntity<ResetPasswordResponse> response = restTemplate.postForEntity("/api/user/resetPassword", request, ResetPasswordResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals("User not found", response.getBody().getCode());
    }

    //post: /api/user/resetPasswordFinalize
    @Test
    @Order(20)
    public void resetPasswordFinalizeUserDNE() {
        ResetPasswordFinalizeRequest request = new ResetPasswordFinalizeRequest("hello", "234", "abc", "abc");
        ResponseEntity<ResetPasswordFinalizeResponse> response = restTemplate.postForEntity("/api/user/resetPasswordFinalize", request, ResetPasswordFinalizeResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User not found", response.getBody().getMessage());
        assertEquals(false, response.getBody().isSuccess());
    }

    @Test
    @Order(21)
    public void resetPasswordFinalizeCodeWrong() {
        ResetPasswordRequest request = new ResetPasswordRequest(userName2);
        ResponseEntity<ResetPasswordResponse> response = restTemplate.postForEntity("/api/user/resetPassword", request, ResetPasswordResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals("User not found", response.getBody().getCode());

        ResetPasswordFinalizeRequest requestt = new ResetPasswordFinalizeRequest(userName2, "234", "abc", "abc");
        ResponseEntity<ResetPasswordFinalizeResponse> responsee = restTemplate.postForEntity("/api/user/resetPasswordFinalize", requestt, ResetPasswordFinalizeResponse.class);
        assertEquals(HttpStatus.OK, responsee.getStatusCode());
        assertEquals("There seems to be a typo in the code or provided password", responsee.getBody().getMessage());
        assertEquals(false, responsee.getBody().isSuccess());
    }

    @Test
    @Order(22)
    public void resetPasswordCombineWrongCode() {
        //step 1:
        ResetPasswordRequest request = new ResetPasswordRequest(userName2);
        ResponseEntity<ResetPasswordResponse> response = restTemplate.postForEntity("/api/user/resetPassword", request, ResetPasswordResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals("User not found", response.getBody().getCode());

        //step 2:
        ResetPasswordFinalizeRequest request2 = new ResetPasswordFinalizeRequest(userName2, "234", "abc", "abc");
        ResponseEntity<ResetPasswordFinalizeResponse> response2 = restTemplate.postForEntity("/api/user/resetPasswordFinalize", request2, ResetPasswordFinalizeResponse.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("There seems to be a typo in the code or provided password", response2.getBody().getMessage());
        assertEquals(false, response2.getBody().isSuccess());
    }

    @Test
    @Order(23)
    public void resetPasswordCombineWrongPassword() {
        //step 1:
        ResetPasswordRequest request = new ResetPasswordRequest(userName2);
        ResponseEntity<ResetPasswordResponse> response = restTemplate.postForEntity("/api/user/resetPassword", request, ResetPasswordResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals("User not found", response.getBody().getCode());

        //step 2:
        ResetPasswordFinalizeRequest request2 = new ResetPasswordFinalizeRequest(userName2, response.getBody().getCode(), passSalted2, "test");
        ResponseEntity<ResetPasswordFinalizeResponse> response2 = restTemplate.postForEntity("/api/user/resetPasswordFinalize", request2, ResetPasswordFinalizeResponse.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("There seems to be a typo in the code or provided password", response2.getBody().getMessage());
        assertEquals(false, response2.getBody().isSuccess());
    }

    @Test
    @Order(24)
    public void resetPasswordCombineSuccess() {
        //step 1:
        ResetPasswordRequest request = new ResetPasswordRequest(userName2);
        ResponseEntity<ResetPasswordResponse> response = restTemplate.postForEntity("/api/user/resetPassword", request, ResetPasswordResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals("User not found", response.getBody().getCode());

        //step 2:
        ResetPasswordFinalizeRequest request2 = new ResetPasswordFinalizeRequest(userName2, response.getBody().getCode(), passSalted2, passSalted2);
        ResponseEntity<ResetPasswordFinalizeResponse> response2 = restTemplate.postForEntity("/api/user/resetPasswordFinalize", request2, ResetPasswordFinalizeResponse.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("Password successfully changed", response2.getBody().getMessage());
        assertEquals(true, response2.getBody().isSuccess());
    }
}
