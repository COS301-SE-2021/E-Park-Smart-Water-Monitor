package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class FindUserByIdRequest {

    private UUID userId;

    public FindUserByIdRequest(@JsonProperty("id")UUID userId) {
        this.userId = userId;
    }

    public FindUserByIdRequest() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
