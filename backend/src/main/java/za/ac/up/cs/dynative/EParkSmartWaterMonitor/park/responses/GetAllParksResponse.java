package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

import java.util.List;

public class GetAllParksResponse {

    private List<Park> allParks;

    public GetAllParksResponse(List<Park> allParks) {
        this.allParks = allParks;
    }

    public List<Park> getAllParks() {
        return allParks;
    }

    public void setAllParks(List<Park> allParks) {
        this.allParks = allParks;
    }

    public GetAllParksResponse(){}

}
