package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.*;

public interface InspectionService {
    public AddInspectionResponse addInspection(AddInspectionRequest request) throws InvalidRequestException;
    public GetWaterSiteInspectionsResponse getWaterSiteInspections(GetWaterSiteInspectionsRequest request);
    public GetDeviceInspectionsResponse getDeviceInspections(GetDeviceInspectionsRequest request);
    public SetInspectionStatusResponse setInspectionStatus(SetInspectionStatusRequest request);
    public SetInspectionCommentsResponse setInspectionComments(SetInspectionCommentsRequest request);
    public SetInspectionDescriptionResponse setInspectionDescription(SetInspectionDescriptionRequest request);
    public GetAllInspectionsResponse getAllInspections();
}
