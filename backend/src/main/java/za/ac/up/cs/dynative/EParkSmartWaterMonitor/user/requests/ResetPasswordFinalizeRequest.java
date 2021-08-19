package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPasswordFinalizeRequest {
    private String username;
    private String resetCode;
    private String newPassword;
    private String newPasswordConfirm;

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }
    public String getResetCode() {
        return resetCode;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public ResetPasswordFinalizeRequest(@JsonProperty("username") String username, @JsonProperty("resetCode") String resetCode, @JsonProperty("newPassword") String newPassword, @JsonProperty("newPasswordConfirmed") String newPasswordConfirm) {
        this.username = username;
        this.resetCode = resetCode;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;

    }










}
