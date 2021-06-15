package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models;

import org.springframework.data.neo4j.core.schema.Id;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;

import java.util.UUID;
import java.util.Date;

public class Inspection {

    @Id
    private UUID id;

    private WaterSourceDevice device;

    private Date dateCreated;

    private Date dateDue;

    private String description;

    private String comments;

    private String status;

    public Inspection(
            WaterSourceDevice device,
            Date dateDue,
            String description) {
        this.id = UUID.randomUUID();
        this.device = device;
        this.dateCreated = new Date();
        this.dateDue = dateDue;
        this.description = description;
        this.comments = "";
        this.status = "NOT STARTED";
    }

    public UUID getId() {
        return this.id;
    }

    public void addComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return this.comments;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
