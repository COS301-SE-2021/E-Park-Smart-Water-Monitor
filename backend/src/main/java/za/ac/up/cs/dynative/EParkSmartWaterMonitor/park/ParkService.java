package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park;

import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.CreateParkResponse;

public interface ParkService {
    CreateParkResponse createPark(CreateParkRequest request);
}
