package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park;

import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.*;

import java.util.UUID;

public interface ParkService {
    CreateParkResponse createPark(CreateParkRequest request) throws InvalidRequestException;
    FindByParkNameResponse findParkByName(FindByParkNameRequest request) throws InvalidRequestException;
    SaveParkResponse savePark(SaveParkRequest request);
    GetParkSitesResponse getParkWaterSites(GetParkSitesRequest getParkSitesRequest);
    FindByParkIdResponse findByParkId(FindByParkIdRequest findByParkIdRequest) throws InvalidRequestException;
    EditParkResponse editPark(EditParkRequest request);
    FindByParkIdResponse findParkById(UUID parkId);
    GetAllParksResponse getAllParks();
}
