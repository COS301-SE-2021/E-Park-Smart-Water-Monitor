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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.CreateParkResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FindParkByName {
    @Mock
    private ParkRepo parkRepo;

    @InjectMocks
    private ParkServiceImpl parkService;
    Double lat =-27.371767;
    Double lon =28.737437;

    @Test
    @DisplayName("Find a park by name, but name is null")
    public void findParkNull(){
        FindByParkNameRequest request= new FindByParkNameRequest("");
        Throwable t= assertThrows(InvalidRequestException.class,()->parkService.findParkByName(request));
        assertEquals("No park name specified",t.getMessage());
    }

    @Test
    @DisplayName("Find a nonexisting park")
    public void findParkDNE(){
        FindByParkNameRequest request= new FindByParkNameRequest("DNE");
        Throwable t= assertThrows(InvalidRequestException.class,()->parkService.findParkByName(request));
        assertEquals("Park not present",t.getMessage());
    }

    //TODO: complete this implementation for finding an xisting park via the name
    /*@Test
    @DisplayName("Find an existing park.")
    public void FindParkExisting() throws InvalidRequestException {
        CreateParkRequest request= new CreateParkRequest("Rietvlei Nature Reserve",lat,lon);
        CreateParkResponse response= parkService.createPark(request);

        FindByParkNameRequest request2 = new FindByParkNameRequest("Rietvlei Nature Reserve");
        FindByParkNameResponse response2= parkService.findParkByName(request2);
        assertNotNull(response2);

        assertEquals("Rietvlei Nature Reserve", response2.getPark().getParkName());
        assertEquals(lat,response2.getPark().getLatitude());
        assertEquals(lon,response2.getPark().getLongitude());
    }*/
}
