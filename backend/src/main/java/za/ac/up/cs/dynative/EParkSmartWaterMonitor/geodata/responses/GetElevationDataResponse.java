package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.responses;

public class GetElevationDataResponse
{

    private String status;
    private Boolean success;
    private  double min;
    private  double max;

    public GetElevationDataResponse(String status, Boolean success, double min, double max)
    {
        this.status = status;
        this.success = success;
        this.min = min;
        this.max = max;
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
