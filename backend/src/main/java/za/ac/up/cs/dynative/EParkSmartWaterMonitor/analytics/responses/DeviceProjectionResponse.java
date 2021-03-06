package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses;


import java.util.ArrayList;

public class DeviceProjectionResponse {

    private String status;
    private Boolean success;
    private String type;
    private int length;
    private ArrayList<Double> optimisticProjections;
    private ArrayList<Double> realisticProjections;
    private ArrayList<Double> concervativeProjections;
    private ArrayList<String> labelDates;

    public DeviceProjectionResponse(String status,
                                    Boolean success,
                                    String type,
                                    int length,
                                    ArrayList<Double> optimisticProjections,
                                    ArrayList<Double> realisticProjections,
                                    ArrayList<Double> concervativeProjections,
                                    ArrayList<String> labelDates) {
        this.status = status;
        this.success = success;
        this.type = type;
        this.length = length;
        this.optimisticProjections = optimisticProjections;
        this.realisticProjections = realisticProjections;
        this.concervativeProjections = concervativeProjections;
        this.labelDates = labelDates;
    }

    public DeviceProjectionResponse() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<Double> getOptimisticProjections() {
        return optimisticProjections;
    }

    public void setOptimisticProjections(ArrayList<Double> optimisticProjections) {
        this.optimisticProjections = optimisticProjections;
    }

    public ArrayList<Double> getRealisticProjections() {
        return realisticProjections;
    }

    public void setRealisticProjections(ArrayList<Double> realisticProjections) {
        this.realisticProjections = realisticProjections;
    }

    public ArrayList<Double> getConcervativeProjections() {
        return concervativeProjections;
    }

    public void setConcervativeProjections(ArrayList<Double> concervativeProjections) {
        this.concervativeProjections = concervativeProjections;
    }

    public ArrayList<String> getLabelDates() {
        return labelDates;
    }

    public void setLabelDates(ArrayList<String> labelDates) {
        this.labelDates = labelDates;
    }
}
