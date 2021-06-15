package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.*;

public interface InspectionService {
    CreateInspectionResponse createInspection(CreateInspectionRequest request);
    GetInspectionsResponse getInspections(GetInspectionsRequest request);
}
