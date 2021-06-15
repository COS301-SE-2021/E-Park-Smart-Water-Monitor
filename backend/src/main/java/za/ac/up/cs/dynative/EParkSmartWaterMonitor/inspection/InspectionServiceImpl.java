package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.FindDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.AddInspectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.GetInspectionsRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.AddInspectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.GetInspectionsResponse;

@Service("InspectionServiceImpl")
public class InspectionServiceImpl implements InspectionService {

    InspectionRepo inspectionRepo;
    DevicesService devicesService;

    @Autowired
    public InspectionServiceImpl(@Qualifier("InspectionRepo") InspectionRepo inspectionRepo, @Qualifier("DeviceServiceImpl") DevicesService devicesService) {
        this.inspectionRepo = inspectionRepo;
        this.devicesService = devicesService;
    }

    @Override
    public AddInspectionResponse addInspection(AddInspectionRequest request) {
        AddInspectionResponse response = new AddInspectionResponse();

        if (request.getDeviceId() != null) {
            FindDeviceResponse findDeviceResponse = devicesService.findDevice(new FindDeviceRequest(request.getDeviceId()));

            if (findDeviceResponse != null && findDeviceResponse.getSuccess()) {
                Inspection inspection = new Inspection(findDeviceResponse.getDevice(), request.getDateDue(), request.getDescription());

                inspectionRepo.save(inspection);

                response.setStatus("Inspection succesfully added!");
                response.setSuccess(true);
            }
            else {
                response.setStatus("Failed to add inspection! Failure to get device!");
                response.setSuccess(false);

                return response;
            }
        }
        else {
            response.setStatus("Failed to add inspection! No deviceId specified!");
            response.setSuccess(false);
        }

        return  response;
    }

    @Override
    public GetInspectionsResponse getInspections(GetInspectionsRequest request) {
        GetInspectionsResponse response = new GetInspectionsResponse();

        return response;
    }
}
