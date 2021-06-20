package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;

import java.util.Collection;
import java.util.List;

public class GetWaterSiteInspectionsResponse {
    private String status;
    private Boolean success;
    private Collection<Inspection> inspectionList;

    public GetWaterSiteInspectionsResponse(String status, Boolean success) {
        this.status = status;
        this.success = success;
    }

    public GetWaterSiteInspectionsResponse() { }

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

    public Collection<Inspection> getInspectionList() { return inspectionList; }

    public void setInspectionList(Collection<Inspection> inspectionList) { this.inspectionList = inspectionList; }
}
