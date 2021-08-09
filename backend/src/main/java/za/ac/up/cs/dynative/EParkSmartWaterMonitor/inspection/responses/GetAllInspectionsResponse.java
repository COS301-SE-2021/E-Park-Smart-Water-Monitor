package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetAllInspectionsResponse
{
    List<UUID> parkId;
    List< List<Inspection>> inspections;

    public GetAllInspectionsResponse(List<Inspection> inspections)
    {
        this.inspections = new ArrayList<>();
        this.parkId = new ArrayList<>();
    }

    public List<UUID> getParkId() {
        return parkId;
    }

    public void setParkId(List<UUID> parkId) {
        this.parkId = parkId;
    }


    public List<List<Inspection>> getInspections() {
        return inspections;
    }

    public void setInspections(List<List<Inspection>> inspections) {
        this.inspections = inspections;
    }



    public void addPark(UUID parkId) {
        this.parkId.add( parkId);
    }

    public void addInspectionSet(List<Inspection>inspections) {
        this.inspections.add(inspections);
    }

}
