package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests;

import java.util.UUID;

public class DeleteInternalRequest {

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DeleteInternalRequest(UUID id) {
        this.id = id;
    }

    public DeleteInternalRequest(){}

    private UUID id;



}
