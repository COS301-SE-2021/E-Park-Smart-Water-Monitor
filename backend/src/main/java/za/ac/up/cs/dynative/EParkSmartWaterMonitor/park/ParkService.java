package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park;

import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.SaveParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.CreateParkResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetParkSitesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.SaveParkResponse;

public interface ParkService {
    CreateParkResponse createPark(CreateParkRequest request);
    FindByParkNameResponse findParkByName(FindByParkNameRequest request);
    SaveParkResponse savePark(SaveParkRequest request);
    GetParkSitesResponse getParkWaterSites(GetParkSitesRequest getParkSitesRequest);
}
