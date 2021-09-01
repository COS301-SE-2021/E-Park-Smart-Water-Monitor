package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    @Autowired
    UserController(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest createUserRequest) throws InvalidRequestException {
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.OK);
    }

    @PostMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestBody FindUserByIdRequest findUserByIdRequest) {
        return new ResponseEntity<>(userService.findUserById(findUserByIdRequest), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) {
        return new ResponseEntity<>(userService.deleteUser(deleteUserRequest), HttpStatus.OK);
    }

    @PutMapping("/editUser")
    public ResponseEntity<Object> editUser(@RequestBody EditUserRequest editUserRequest) {
        return new ResponseEntity<>(userService.editUser(editUserRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) throws InvalidRequestException {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws InvalidRequestException {
        return new ResponseEntity<>(userService.resetPassword(resetPasswordRequest), HttpStatus.OK);
    }

    @PostMapping("/deleteInternal")
    public ResponseEntity<Object> deleteInternal(@RequestBody DeleteUserRequest deleteUserRequest) {
        return new ResponseEntity<>(userService.deleteUser(deleteUserRequest), HttpStatus.OK);
    }

    @PostMapping("/resetPasswordFinalize")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordFinalizeRequest resetPasswordFinalizeRequest) {
        return new ResponseEntity<>(userService.resetPasswordFinalize(resetPasswordFinalizeRequest), HttpStatus.OK);
    }

}
