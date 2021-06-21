package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.FindDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.*;
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
    public AddInspectionResponse addInspection(AddInspectionRequest request) throws InvalidRequestException {
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
    public GetWaterSiteInspectionsResponse getWaterSiteInspections(GetWaterSiteInspectionsRequest request) {
        GetWaterSiteInspectionsResponse response = new GetWaterSiteInspectionsResponse();

        if (request.getWaterSiteId() == null) {
            response.setStatus("Failed to get inspection! Invalid waterSiteId!");
            response.setSuccess(false);

            return response;
        }

        response.setInspectionList(inspectionRepo.findInspectionsByWaterSiteId(request.getWaterSiteId()));

        response.setStatus("Inspections retrieved successfully!");
        response.setSuccess(true);

        return response;
    }

    @Override
    public GetDeviceInspectionsResponse getDeviceInspections(GetDeviceInspectionsRequest request) {
        GetDeviceInspectionsResponse response = new GetDeviceInspectionsResponse();

        if (request.getDeviceId() == null) {
            response.setStatus("Failed to get inspection! Invalid deviceId!");
            response.setSuccess(false);

            return response;
        }

        response.setInspectionList(inspectionRepo.getInspectionByDeviceId(request.getDeviceId()));

        response.setStatus("Inspections retrieved successfully!");
        response.setSuccess(true);

        return response;
    }

    @Override
    public SetInspectionStatusResponse setInspectionStatus(SetInspectionStatusRequest request) {
        SetInspectionStatusResponse response = new SetInspectionStatusResponse();

        if (request.getInspectionId() == null) {
            response.setStatus("Failed to set inspection status! Invalid inspectionId!");
            response.setSuccess(false);

            return response;
        }

        Inspection inspection = inspectionRepo.findInspectionById(request.getInspectionId());

        if (inspection == null) {
            response.setStatus("Failed to set inspection status! Inspection not found!");
            response.setSuccess(false);

            return response;
        }

        inspection.setStatus(request.getStatus());

        inspectionRepo.save(inspection);

        response.setStatus("Inspection status successfully set!");
        response.setSuccess(true);

        return response;
    }


    @Override
    public SetInspectionCommentsResponse setInspectionComments(SetInspectionCommentsRequest request) {
        SetInspectionCommentsResponse response = new SetInspectionCommentsResponse();

        if (request.getInspectionId() == null) {
            response.setStatus("Failed to set inspection comments! Invalid inspectionId!");
            response.setSuccess(false);

            return response;
        }

        Inspection inspection = inspectionRepo.findInspectionById(request.getInspectionId());

        if (inspection == null) {
            response.setStatus("Failed to set inspection comments! Inspection not found!");
            response.setSuccess(false);

            return response;
        }

        inspection.setComments(request.getComments());

        inspectionRepo.save(inspection);

        response.setStatus("Inspection comments successfully set!");
        response.setSuccess(true);

        return response;
    }
}
