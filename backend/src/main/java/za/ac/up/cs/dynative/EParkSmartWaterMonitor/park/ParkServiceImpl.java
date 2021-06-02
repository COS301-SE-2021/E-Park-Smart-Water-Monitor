package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.SaveParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.CreateParkResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetParkSitesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.SaveParkResponse;

import java.util.UUID;

@Service("ParkService")
public class ParkServiceImpl implements ParkService {

    private final ParkRepo parkRepo;

    @Autowired
    public ParkServiceImpl(@Qualifier("ParkRepo") ParkRepo parkRepo) {
        this.parkRepo = parkRepo;
    }

    @Override
    public CreateParkResponse createPark(CreateParkRequest request) {

        CreateParkResponse response = new CreateParkResponse();
        if (!request.getParkName().equals("")) {
            Park park = new Park(request.getParkName(),request.getLatitude(),request.getLongitude());
            parkRepo.save(park);

            response.setStatus("Park Added!");
            response.setSuccess(true);
        }
        else {
            response.setStatus("No Park Name specified!");
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public FindByParkNameResponse findParkByName(FindByParkNameRequest request) {
        if (!request.getParkName().equals("")) {
            Park park = parkRepo.findParkByParkName(request.getParkName());
            return new FindByParkNameResponse(park);
        }
        else return new FindByParkNameResponse(null);
    }

    @Override
    public SaveParkResponse savePark(SaveParkRequest request) {
        if (request.getPark() != null) {
            parkRepo.save(request.getPark());
            return new SaveParkResponse("Saved park successfully",true);
        }
        return new SaveParkResponse("Error in saving park!",false);
    }

    @Override
    public GetParkSitesResponse getParkWaterSites(GetParkSitesRequest request) {
        GetParkSitesResponse response = new GetParkSitesResponse();
        if (request.getParkId() != null) {
            Park park = parkRepo.findParkById(request.getParkId());
            if (park!=null) {
                response.setSite(park.getParkWaterSites());
                response.setSuccess(true);
                response.setStatus("Park Sites and their IoT devices");
            }
        }
        else {
            response.setStatus("Failed to the sites!");
            response.setSuccess(false);
        }
        return response;
    }
}