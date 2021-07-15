package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service("ParkService")
public class ParkServiceImpl implements ParkService {

    private final ParkRepo parkRepo;

    @Autowired
    public ParkServiceImpl(@Qualifier("ParkRepo") ParkRepo parkRepo) {
        this.parkRepo = parkRepo;
    }

    @Override
    public CreateParkResponse createPark(CreateParkRequest request) throws InvalidRequestException {

        CreateParkResponse response = new CreateParkResponse();
        if (!request.getParkName().equals("")) {

            if (parkRepo.findParkByParkName(request.getParkName()).size()>0){
                response.setStatus("Park "+request.getParkName()+" already exists!");
                response.setSuccess(false);
                return response;
            }

            Park park = new Park(request.getParkName(),request.getLatitude(),request.getLongitude());
            parkRepo.save(park);

            response.setStatus("Park "+request.getParkName()+" Added!");
            response.setSuccess(true);


        }
        else {
            response.setStatus("No Park Name specified!");
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public FindByParkNameResponse findParkByName(FindByParkNameRequest request) throws InvalidRequestException {
        if (!request.getParkName().equals("")) {
            List<Park> park = parkRepo.findParkByParkName(request.getParkName());
            if (park==null){
                return new FindByParkNameResponse(null);
            }else {
                return new FindByParkNameResponse((Park) park.toArray()[0]);
            }
        }else{
            return new FindByParkNameResponse(null);
        }
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
    public GetParkSitesResponse getParkWaterSites(GetParkSitesRequest request) throws InvalidRequestException {
        GetParkSitesResponse response = new GetParkSitesResponse();
        if (request.getParkId() != null) {
            Park park = parkRepo.findParkById(request.getParkId());
            if (park!=null) {
                response.setSite(park.getParkWaterSites());
                response.setSuccess(true);
                response.setStatus("Park Sites and their IoT devices");
            }else{
                response.setStatus("Park not present");
                response.setSuccess(false);
            }
        }
        else {
            response.setStatus("No park id specified");
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public FindByParkIdResponse findByParkId(FindByParkIdRequest request) throws InvalidRequestException {
        FindByParkIdResponse response = new FindByParkIdResponse();
        if (request.getParkId() != null) {
            Park park = parkRepo.findParkById(request.getParkId());
            if (park != null) {
                response.setStatus(park);
                response.setSuccess(true);
            }else{
                response.setStatus(null);
                response.setSuccess(false);            }
        }else{
            response.setStatus(null);
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public EditParkResponse editPark(EditParkRequest request) throws InvalidRequestException {
        if (request.getParkId()==null){
            throw new InvalidRequestException("No id specified");
        }
        Park park = parkRepo.findParkById(request.getParkId());

        EditParkResponse response = new EditParkResponse();
        if (park!=null) {
            if (!request.getParkName().equals("")) {
                park.setParkName(request.getParkName());
            }
            if (!request.getLatitude().equals("")) {
                park.setLatitude(Double.parseDouble(request.getLatitude()));
            }
            if (!request.getLongitude().equals("")) {
                park.setLongitude(Double.parseDouble(request.getLongitude()));
            }

            parkRepo.save(park);
            response.setStatus("Park details changed.");
            response.setSuccess(true);
            return response;
        }
        else
        {
            throw new InvalidRequestException("No park with that id exists.");
            /*response.setStatus("No park with that id exists.");
            response.setSuccess(false);
            return response;*/
        }


    }

    @Override
    public FindByParkIdResponse findParkById(UUID parkId) {
        return new FindByParkIdResponse(true,parkRepo.findParkById(parkId));
    }

    @Override
    public GetAllParksResponse getAllParks() {
        return new GetAllParksResponse(parkRepo.getAllParks());
    }
}
