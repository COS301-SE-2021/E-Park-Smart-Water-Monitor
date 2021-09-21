package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.GeoJSON;

public class GetLossDataResponse
{
    private String status;
    private Boolean success;
    private  double min;
    private  double max;
    private GeoJSON geoJSON;

    public GetLossDataResponse(String status, Boolean success, double min, double max, GeoJSON geoJSON)
    {
        this.status = status;
        this.success = success;
        this.min = min;
        this.max = max;
        this.geoJSON = geoJSON;
    }

    public GeoJSON getGeoJSON() {
        return geoJSON;
    }

    public void setGeoJSON(GeoJSON geoJSON) {
        this.geoJSON = geoJSON;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
