package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.DeleteUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.EditUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.LoginRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.*;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
    EditUserResponse editUser(EditUserRequest request);
    LoginResponse loginUser(LoginRequest request);
    GetAllUsersResponse getAllUsers();
    User findUserByUserName(String username);
    DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest);
}
