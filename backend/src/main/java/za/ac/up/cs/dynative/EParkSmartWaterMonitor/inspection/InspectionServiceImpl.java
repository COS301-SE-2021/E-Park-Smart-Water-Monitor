package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.AddInspectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.GetInspectionsRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.AddInspectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.GetInspectionsResponse;

@Service("InspectionServiceImpl")
public class InspectionServiceImpl implements InspectionService {

    private InspectionRepo inspectionRepo;

    @Autowired
    public InspectionServiceImpl(@Qualifier("InspectionRepo") InspectionRepo inspectionRepo) {
        this.inspectionRepo = inspectionRepo;
    }

    @Override
    public AddInspectionResponse addInspection(AddInspectionRequest request) {
        AddInspectionResponse response = new AddInspectionResponse();

        return  response;
    }

    @Override
    public GetInspectionsResponse getInspections(GetInspectionsRequest request) {
        GetInspectionsResponse response = new GetInspectionsResponse();

        return response;
    }
}
