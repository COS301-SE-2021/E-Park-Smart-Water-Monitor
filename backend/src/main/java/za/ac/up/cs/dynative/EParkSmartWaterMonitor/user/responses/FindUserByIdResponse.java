package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;


import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

public class FindUserByIdResponse {

    private Boolean success;
    private User user;

    public FindUserByIdResponse(Boolean success, User user) {
        this.success = success;
        this.user = user;
        hidePassword();
    }

    private void hidePassword() {
        if (this.user != null)
            this.user.setPassword("");
    }

    public FindUserByIdResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setStatus(User user) {
        this.user = user;
    }
}
