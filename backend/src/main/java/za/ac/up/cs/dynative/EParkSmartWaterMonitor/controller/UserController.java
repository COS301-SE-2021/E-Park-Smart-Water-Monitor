package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.*;


/**
 * This class will map all the http requests made related to users to their
 * respected functions in the user service implementation.
 * The http methods that will be used: post, put, get, and delete.
 */


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * The userService will be used to call functions in the user service implementation.
     */
    UserService userService;

    /**
     * This constructor will set the userService variable defined previously so that it is not of null value
     * @param userService will be set and used across the rest of this class.
     */
    @Autowired
    UserController(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    /**
     * This post request will be used to create a new user in the system.
     * @param createUserRequest will contain the following user detail: park id, id number, email, name and surname, their role and their cell number.
     * @return A CreateUserResponse will be sent back containing the user's system id and the request's success value and status.
     */
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest createUserRequest) throws InvalidRequestException {
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.OK);
    }

    /**
     * THis post mapping will be used to retrieve a specific user's details.
     * @param findUserByIdRequest will contain the user's system id to be used as input for the service implementation.
     * @return A FindUserByIdResponse will be returned and it contains the user's details along with a request status.
     */
    @PostMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestBody FindUserByIdRequest findUserByIdRequest) {
        return new ResponseEntity<>(userService.findUserById(findUserByIdRequest), HttpStatus.OK);
    }

    /**
     * This delete request will be used to delete the specified user.
     * @param deleteUserRequest contains the user id.
     * @return A DeletUserResponse will be returned containing the success value and status of the request.
     */
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) {
        return new ResponseEntity<>(userService.deleteUser(deleteUserRequest), HttpStatus.OK);
    }

    /**
     * This put request will be used to update an existing user's details.
     * @param editUserRequest will contain the user's details to be changed. Details that may be changed: username, id number, surname, name
     *                        email, cell phone number and their role.
     * @return An EditUserResponse containing the success value and status of the request.
     */
    @PutMapping("/editUser")
    public ResponseEntity<Object> editUser(@RequestBody EditUserRequest editUserRequest) {
        return new ResponseEntity<>(userService.editUser(editUserRequest), HttpStatus.OK);
    }

    /**
     * This post request will be used to log a user in to the system.
     * @param loginRequest  will contain the username and a salted password.
     * @return A LoginResponse object will be sent back containing the user's details, their jwt token and a success value.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) throws InvalidRequestException {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    /**
     * This get request will be used to the  information of all the users.
     * @return A GetAllUsersResponse containing the list of users will be sent back to the caller.
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * This post request will be used to acquire a code to reset the password.
     * @param resetPasswordRequest the object will contain the username.
     * @return A ResetPasswordResponse will be sent back to the caller containing the code.
     */
    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws InvalidRequestException {
        return new ResponseEntity<>(userService.resetPassword(resetPasswordRequest), HttpStatus.OK);
    }

    /**
     * This is solely for testing usage.
     */
    @PostMapping("/deleteInternal")
    public ResponseEntity<Object> deleteInternal(@RequestBody DeleteUserRequest deleteUserRequest) {
        return new ResponseEntity<>(userService.deleteUser(deleteUserRequest), HttpStatus.OK);
    }

    /**
     * This post request will be used to finalize the resetting of a password.
     * @param resetPasswordFinalizeRequest will contain the username, the passwords and the reset code generated earlier by the system.
     * @return A ResetPasswordFinalizeResponse will be sent back to the caller containing the success value and a status message.
     */
    @PostMapping("/resetPasswordFinalize")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordFinalizeRequest resetPasswordFinalizeRequest) {
        return new ResponseEntity<>(userService.resetPasswordFinalize(resetPasswordFinalizeRequest), HttpStatus.OK);
    }

}
