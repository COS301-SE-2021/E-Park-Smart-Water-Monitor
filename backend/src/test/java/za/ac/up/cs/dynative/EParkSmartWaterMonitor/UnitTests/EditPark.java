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
    public void EditParkIdDNE(){
        //set up repo
        UUID testID= UUID.randomUUID();
        Mockito.when(parkRepo.findParkById(testID)).thenReturn(null);

        //test
        EditParkRequest request= new EditParkRequest(testID,"Pname","50","50");
        EditParkResponse response=parkService.editPark(request);
        assertNotNull(response);
        assertEquals("No park with that id exists.",response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Try and edit a park but no park id specified")
    public void EditParkIdNull(){
        EditParkRequest request= new EditParkRequest(null,"Pname","50","50");
        EditParkResponse response=parkService.editPark(request);
        assertNotNull(response);
        assertEquals("No park id specified",response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Edit the park with success")
    public void EditParkSuccess(){
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
