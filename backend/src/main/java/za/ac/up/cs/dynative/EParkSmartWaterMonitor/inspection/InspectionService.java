package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.*;

public interface InspectionService {
    public AddInspectionResponse addInspection(AddInspectionRequest request);
    public GetWaterSiteInspectionsResponse getWaterSiteInspections(GetWaterSiteInspectionsRequest request);
}
