package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.WeatherData;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.EditParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.EditParkResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EditPark {
    @Mock
    private ParkRepo parkRepo;

    @InjectMocks
    private ParkServiceImpl parkService;

    @Test
    @DisplayName("Try and edit a park but no park with that id exists")
    public void EditDNE(){
        //set up repo
        UUID testID= UUID.randomUUID();
        Mockito.when(parkRepo.findParkById(testID)).thenReturn(null);

        //test
        EditParkRequest request= new EditParkRequest(testID,"Pname","50","50");
        Throwable t= assertThrows(InvalidRequestException.class,()->parkService.editPark(request));
        assertEquals("No park with that id exists.",t.getMessage());
    }

    @Test
    @DisplayName("Try and edit a park but no park id specified")
    public void EditNull(){
        EditParkRequest request= new EditParkRequest(null,"Pname","50","50");
        Throwable t= assertThrows(InvalidRequestException.class,()->parkService.editPark(request));
        assertEquals("No id specified",t.getMessage());
    }

    @Test
    @DisplayName("Edit all fields, but we have more than one park")
    public void EditAllWithMultipleParksPresent() throws InvalidRequestException {
        //setup repo
        Set<WaterSite> sites= new HashSet<>();
        Set<WeatherData> weather= new HashSet<>();
        Park p1= new Park("Unit tests",25,36,weather,sites);
        Mockito.when(parkRepo.findParkById(p1.getId())).thenReturn(p1);

        //testing
        EditParkRequest request = new EditParkRequest(p1.getId(),"Unit Testing","29","11");
        assertNotNull(request);
        EditParkResponse response = parkService.editPark(request);
        assertNotNull(response);
        assertEquals("Park details changed.",response.getStatus());
        assertEquals(true,response.getSuccess());
    }

    @Test
    @DisplayName("Edit the name fields, but we have more than one park")
    public void EditAllName() throws InvalidRequestException {
        //setup repo
        Set<WaterSite> sites= new HashSet<>();
        Set<WeatherData> weather= new HashSet<>();
        Park p2= new Park("Unit tests",25,36,weather,sites);
        Mockito.when(parkRepo.findParkById(p2.getId())).thenReturn(p2);
        UUID id= p2.getId();

        //testing
        EditParkRequest request = new EditParkRequest(id,"Unit Testing","25","36");
        assertNotNull(request);
        EditParkResponse response = parkService.editPark(request);
        assertNotNull(response);
        assertEquals("Park details changed.",response.getStatus());
        assertEquals(true,response.getSuccess());
    }
}
