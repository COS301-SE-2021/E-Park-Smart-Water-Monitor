package UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.DeleteParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.DeleteParkResponse;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DeletePark {
    @Mock
    private ParkRepo parkRepo;

    @InjectMocks
    private ParkServiceImpl parkService;
    
    @Test
    @DisplayName("Delete park but no id is specified")
    public void DeleteParkIdNull(){
        DeleteParkRequest request = new DeleteParkRequest(null);
        DeleteParkResponse response = parkService.deletePark(request);
        assertNotNull(response);
        assertEquals("No park id specified.",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Delete park but no id is specified")
    public void DeleteParkDNE(){
        //setup
        Mockito.when(parkRepo.findParkById(Mockito.any())).thenReturn(null);
        
        //test
        DeleteParkRequest request = new DeleteParkRequest(UUID.randomUUID());
        DeleteParkResponse response = parkService.deletePark(request);
        assertNotNull(response);
        assertEquals("No park with this id exists.",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Delete park with success")
    public void DeleteParkSuccess(){
        //setup
        Mockito.when(parkRepo.findParkById(Mockito.any())).thenReturn(new Park());

        //test
        DeleteParkRequest request = new DeleteParkRequest(UUID.randomUUID());
        DeleteParkResponse response = parkService.deletePark(request);
        assertNotNull(response);
        assertEquals("Successfully deleted the park and all related entities.",response.getStatus());
        assertEquals(true,response.getSuccess());
    }
}
