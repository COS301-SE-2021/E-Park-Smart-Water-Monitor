package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;

import java.util.UUID;
import java.util.Date;

public class Inspection {

    @Id
    private UUID id;

    @Relationship(type = "PERFORMED_ON", direction = Relationship.Direction.OUTGOING)
    private Device device;

    private UUID deviceId;

    private UUID waterSiteId;

    private Date dateCreated;

    private Date dateDue;

    private String description;

    private String comments;

    private String status;

    public Inspection(
            Device device,
            UUID waterSiteId,
            Date dateDue,
            String description) {
        this.id = UUID.randomUUID();
        this.device = device;
        this.waterSiteId = waterSiteId;
        this.dateCreated = new Date();
        this.dateDue = dateDue;
        this.description = description;
        this.comments = "";
        this.status = "NOT STARTED";
    }

    @Override
    public String toString() {
        return "Inspection{" +
                "id=" + id +
                ", device=" + device +
                ", deviceId=" + deviceId +
                ", waterSiteId=" + waterSiteId +
                ", dateCreated=" + dateCreated +
                ", dateDue=" + dateDue +
                ", description='" + description + '\'' +
                ", comments='" + comments + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public UUID getId() {
        return this.id;
    }

    public void setComments(String comments) {
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

    public Date getDateCreated() { return  this.dateCreated; }

    public Date getDateDue() { return this.dateDue; }

    public void setDateDue(Date dateDue) { this.dateDue = dateDue; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public UUID getWaterSiteId() {
        return waterSiteId;
    }

    public void setWaterSiteId(UUID waterSiteId) {
        this.waterSiteId = waterSiteId;
    }
}
