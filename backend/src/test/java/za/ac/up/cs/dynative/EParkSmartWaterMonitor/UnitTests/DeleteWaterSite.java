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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.DeleteWaterSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.DeleteWaterSiteResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DeleteWaterSite {

    @Mock
    WaterSiteRepo repo;

    @Mock(name="parkService")
    ParkServiceImpl parkService;

    @InjectMocks
    WaterSiteServicesImpl waterSiteServices;

    @Test
    @DisplayName("Attempt to delete a water site but no id is specified")
    public void DeleteWaterSiteIdNull(){
        DeleteWaterSiteRequest request = new DeleteWaterSiteRequest(null);
        DeleteWaterSiteResponse response = waterSiteServices.deleteWaterSite(request);
        assertNotNull(response);
        assertEquals(false, response.getSuccess());
        assertEquals("No watersite id specified.", response.getStatus());
    }

    @Test
    @DisplayName("Attempt to delete a water site that does not exist")
    public void DeleteWaterSiteDNE(){
        //setup
        Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.empty());

        //test
        DeleteWaterSiteRequest request = new DeleteWaterSiteRequest(UUID.randomUUID());
        DeleteWaterSiteResponse response = waterSiteServices.deleteWaterSite(request);
        assertNotNull(response);
        assertEquals(false, response.getSuccess());
        assertEquals("No watersite with this id exists.", response.getStatus());
    }

    @Test
    @DisplayName("Attempt to delete a water site and succeed")
    public void DeleteWaterSiteSuccess(){
        //setup
        Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.of(new WaterSite()));

        //test
        DeleteWaterSiteRequest request = new DeleteWaterSiteRequest(UUID.randomUUID());
        DeleteWaterSiteResponse response = waterSiteServices.deleteWaterSite(request);
        assertNotNull(response);
        assertEquals(true, response.getSuccess());
        assertEquals("Successfully deleted the watersite and all related entities.", response.getStatus());
    }
}
