package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class EditParkRequest
{
    private UUID parkId;
    private String parkName;
    private String latitude;


    public EditParkRequest(
            @JsonProperty("parkId") UUID parkId,
            @JsonProperty("parkName") String parkName,
            @JsonProperty("latitude") String latitude,
            @JsonProperty("longitude") String longitude) {

        this.parkId = parkId;
        this.parkName = parkName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getParkId() {

        return parkId;
    }

    public void setParkId(UUID parkId) {
        this.parkId = parkId;
    }


    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private String longitude;
}
