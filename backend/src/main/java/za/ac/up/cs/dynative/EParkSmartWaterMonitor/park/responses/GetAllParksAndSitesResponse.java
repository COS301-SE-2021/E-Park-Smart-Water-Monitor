package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

import java.util.List;

public class GetAllParksAndSitesResponse
{
    private List<Park> parks;

    public GetAllParksAndSitesResponse(List<Park> parks) {
        this.parks = parks;
    }

    public void setParks(List<Park> parks) {
        this.parks = parks;
    }

    public List<Park> getParks() {
        return parks;
    }

    public GetAllParksAndSitesResponse(){}
}
