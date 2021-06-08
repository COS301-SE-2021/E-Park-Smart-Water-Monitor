package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park;

import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.*;

import java.util.UUID;

public interface ParkService {
    CreateParkResponse createPark(CreateParkRequest request);
    FindByParkNameResponse findParkByName(FindByParkNameRequest request);
    SaveParkResponse savePark(SaveParkRequest request);
    GetParkSitesResponse getParkWaterSites(GetParkSitesRequest getParkSitesRequest);
    FindByParkIdResponse findByParkId(FindByParkIdRequest findByParkIdRequest);
    FindByParkIdResponse findParkById(UUID parkId);
}
