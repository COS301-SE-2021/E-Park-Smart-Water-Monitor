package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.*;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
    EditUserResponse editUser(EditUserRequest request);
    LoginResponse loginUser(LoginRequest request);
    GetAllUsersResponse getAllUsers();
    User findUserByUserName(String username);
    DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest);
    FindUserByIdResponse findUserById(FindUserByIdRequest findUserByIdRequest);
}
