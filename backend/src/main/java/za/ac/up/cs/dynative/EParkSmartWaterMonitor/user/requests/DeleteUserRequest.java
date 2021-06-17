package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DeleteUserRequest {
    private UUID id;

    public DeleteUserRequest() {
    }

    public DeleteUserRequest(@JsonProperty("id") UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
