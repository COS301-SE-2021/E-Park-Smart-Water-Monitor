package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SetPasswordRequest {
    private String username;
    private String newPassword;
    private String confirmPassword;

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    public SetPasswordRequest(@JsonProperty("username") String username,@JsonProperty("newPassword") String newPassword,
                              @JsonProperty("confirmPassword") String confirmPassword){
        this.username= username;
        this.newPassword= newPassword;
        this.confirmPassword= confirmPassword;
    }
}
