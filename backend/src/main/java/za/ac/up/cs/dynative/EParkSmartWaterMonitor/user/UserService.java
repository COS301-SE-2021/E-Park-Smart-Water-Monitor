package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.*;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request) throws InvalidRequestException;
    EditUserResponse editUser(EditUserRequest request);
    LoginResponse loginUser(LoginRequest request) throws InvalidRequestException;
    GetAllUsersResponse getAllUsers();
    User findUserByUserName(String username);
    List<User> findUsersRelatedToDevice(String dName);
    DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest);
    FindUserByIdResponse findUserById(FindUserByIdRequest findUserByIdRequest);
    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws InvalidRequestException;
    ResetPasswordFinalizeResponse resetPasswordFinalize(ResetPasswordFinalizeRequest resetPasswordFinalizeRequest);
}
