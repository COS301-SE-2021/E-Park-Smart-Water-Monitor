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

            //TODO: if the park name is already used throw an InvalidRequestException("Park name specified already exists!")
            // NB the message must be exactly correct!!!
            // See the else for details how to throw the Exception

            Park park = new Park(request.getParkName(),request.getLatitude(),request.getLongitude());
            parkRepo.save(park);

            response.setStatus("Park "+request.getParkName()+" Added!");
            response.setSuccess(true);


        }
        else {
            response.setStatus("No Park Name specified!");
            response.setSuccess(false);
            throw new InvalidRequestException("No park name specified!");
        }
        return response;
    }

    @Override
    public FindByParkNameResponse findParkByName(FindByParkNameRequest request) throws InvalidRequestException {
        if (!request.getParkName().equals("")) {
            List<Park> park = parkRepo.findParkByParkName(request.getParkName());
            if (park.size()==0){
                throw new InvalidRequestException("Park not present");
            }else {
                return new FindByParkNameResponse((Park) park.toArray()[0]);
            }
        }else{
            throw new InvalidRequestException("No park name specified");
            //return new FindByParkNameResponse(null);
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
                throw new InvalidRequestException("Park not present");
            }
        }
        else {
            response.setStatus("Failed to the sites!");
            response.setSuccess(false);
            throw new InvalidRequestException("No ID provided");
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
            }else{
                throw new InvalidRequestException("Park not present");
            }
        }else{
            throw new InvalidRequestException("No park id specified");
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
