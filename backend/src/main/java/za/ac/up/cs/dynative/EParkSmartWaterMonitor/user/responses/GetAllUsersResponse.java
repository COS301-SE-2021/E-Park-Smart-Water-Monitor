package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.List;

public class GetAllUsersResponse {

    private List<User> allUsers;
    public GetAllUsersResponse(List<User> allUsers) {
        this.allUsers = allUsers;
        hidePassword();
    }

    private void hidePassword() {
        for (User u : allUsers) {
            u.setPassword("");
        }
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }
}
