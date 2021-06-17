package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.EditUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.LoginRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.CreateUserResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.EditUserResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllUsersResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.LoginResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request) throws InvalidRequestException;
    EditUserResponse editUser(EditUserRequest request);
    LoginResponse loginUser(LoginRequest request);
    GetAllUsersResponse getAllUsers();
    User findUserByUserName(String username);
}
