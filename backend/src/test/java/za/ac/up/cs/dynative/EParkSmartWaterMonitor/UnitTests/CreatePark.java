package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.CreateParkResponse;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CreatePark {


    @Mock
    private ParkRepo parkRepo;

    @InjectMocks
    private ParkServiceImpl parkService;

    Double lat =-27.371767;
    Double lon =28.737437;


    @Test
    @DisplayName("Creating a park where the name is not set.")
    public void createParkNameNull() {
        CreateParkRequest request= new CreateParkRequest("",lat,lon);
        Throwable t= assertThrows(InvalidRequestException.class,()->parkService.createPark(request));
        assertEquals("No park name specified!",t.getMessage());
    }

    @Test
    @DisplayName("Creating a new park successfully")
    public void createPark() throws InvalidRequestException {
        CreateParkRequest request= new CreateParkRequest("Unit Testing",lat,lon);
        assertNotNull(request);
        CreateParkResponse response= parkService.createPark(request);
        assertNotNull(response);
        assertEquals("Park "+request.getParkName()+" Added!",response.getStatus());
        assertEquals(true,response.getSuccess());
    }


    //TODO: uncomment when the createPark function in the ParkServiceImpl is fixed.
    /*@Test
    @DisplayName("Creating a park with an existing name")
    public void createParkAE()  {
        CreateParkRequest request= new CreateParkRequest("Unit Testing",lat,lon);
        Throwable t= assertThrows(InvalidRequestException.class,()->parkService.createPark(request));
        assertEquals("Park name specified already exists!",t.getMessage());

    }*/

}
