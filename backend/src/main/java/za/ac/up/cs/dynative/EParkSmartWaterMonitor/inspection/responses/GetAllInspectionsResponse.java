package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;

import java.util.List;

public class GetAllInspectionsResponse
{
    List<Inspection> inspections;

    public GetAllInspectionsResponse(List<Inspection> inspections)
    {
        this.inspections = inspections;
    }

    public List<Inspection> getInspections() {
        return inspections;
    }

    public void setInspections(List<Inspection> inspections) {
        this.inspections = inspections;
    }
}
