package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.DeleteUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.EditUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.LoginRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    @Autowired
    UserController(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.OK);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<Object> createUser(@RequestBody DeleteUserRequest deleteUserRequest) {
        return new ResponseEntity<>(userService.deleteUser(deleteUserRequest), HttpStatus.OK);
    }

    @PostMapping("/editUser")
    public ResponseEntity<Object> createUser(@RequestBody EditUserRequest editUserRequest) {
        return new ResponseEntity<>(userService.editUser(editUserRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
