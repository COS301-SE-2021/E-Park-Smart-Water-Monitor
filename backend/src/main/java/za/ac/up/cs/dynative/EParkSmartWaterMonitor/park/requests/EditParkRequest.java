package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests;

public class EditParkRequest
{
    private String parkName;
    private String latitude;

    public EditParkRequest(String parkName, String latitude, String longitude) {
        this.parkName = parkName;
        this.latitude = latitude;
        this.longitude = longitude;
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
