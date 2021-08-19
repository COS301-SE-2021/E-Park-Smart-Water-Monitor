package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPasswordRequest {
    private String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public ResetPasswordRequest(@JsonProperty("username") String username){
        this.username = username;
    }
}
