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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.SaveSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;

@Service("InspectionServiceImpl")
public class InspectionServiceImpl implements InspectionService {

    InspectionRepo inspectionRepo;
    DevicesService devicesService;
    WaterSiteService waterSiteService;

    @Autowired
    public InspectionServiceImpl(@Qualifier("InspectionRepo") InspectionRepo inspectionRepo,
                                 @Qualifier("DeviceServiceImpl") DevicesService devicesService,
                                 @Qualifier("WaterSiteServiceImpl") WaterSiteService waterSiteService) {
        this.inspectionRepo = inspectionRepo;
        this.devicesService = devicesService;
        this.waterSiteService = waterSiteService;
    }

    @Override
    public AddInspectionResponse addInspection(AddInspectionRequest request) {
        AddInspectionResponse response = new AddInspectionResponse();

        if (request.getDeviceId() == null) {
            response.setStatus("Failed to add inspection! No deviceId specified!");
            response.setSuccess(false);

            return response;
        }

        FindDeviceResponse findDeviceResponse = devicesService.findDevice(new FindDeviceRequest(request.getDeviceId()));

        if (findDeviceResponse == null || !findDeviceResponse.getSuccess()) {
            response.setStatus("Failed to add inspection! Failure to get device!");
            response.setSuccess(false);

            return response;
        }

        GetSiteByIdResponse getSiteByIdResponse = waterSiteService.getSiteById(new GetSiteByIdRequest((request.getWaterSiteId())));

        if (getSiteByIdResponse == null || !getSiteByIdResponse.getSuccess()) {
            response.setStatus("Failed to add inspection! Failure to get site!");
            response.setSuccess(false);

            return response;
        }

        Inspection inspection = new Inspection(findDeviceResponse.getDevice(), request.getWaterSiteId(), request.getDateDue(), request.getDescription());
        inspectionRepo.save(inspection);

        getSiteByIdResponse.getSite().addInspection(inspection);

        waterSiteService.saveSite(new SaveSiteRequest(getSiteByIdResponse.getSite()));

        response.setStatus("Inspection successfully added!");
        response.setSuccess(true);

        return  response;
    }

    @Override
    public GetInspectionsResponse getInspections(GetInspectionsRequest request) {
        GetInspectionsResponse response = new GetInspectionsResponse();

        return response;
    }
}
