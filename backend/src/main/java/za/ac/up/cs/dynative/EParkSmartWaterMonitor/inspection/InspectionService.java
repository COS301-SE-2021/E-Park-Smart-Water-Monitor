package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.*;

public interface InspectionService {
    public AddInspectionResponse addInspection(AddInspectionRequest request);
    public GetWaterSiteInspectionsResponse getWaterSiteInspections(GetWaterSiteInspectionsRequest request);
    public GetDeviceInspectionsResponse getDeviceInspections(GetDeviceInspectionsRequest request);
    public SetInspectionStatusResponse setInspectionStatus(SetInspectionStatusRequest request);
    public SetInspectionCommentsResponse setInspectionComments(SetInspectionCommentsRequest request);
}
