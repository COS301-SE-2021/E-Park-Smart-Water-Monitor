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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.EditWaterSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.EditWaterSiteResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EditWaterSite {

    @Mock
    WaterSiteRepo repo;

    @Mock(name="parkService")
    ParkServiceImpl parkService;

    @InjectMocks
    WaterSiteServicesImpl waterSiteServices;

    @Test
    @DisplayName("Attempt to edit a water site but no id is specified")
    public void EditSiteIdNull(){
        EditWaterSiteRequest request = new EditWaterSiteRequest(null,"",0,0);
        EditWaterSiteResponse response = waterSiteServices.editWaterSite(request);
        assertNotNull(response);
        assertEquals("No watersite id specified", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Attempt to edit a water site that does not exist")
    public void EditSiteDNE(){
        //setup
        Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.empty());

        //test
        EditWaterSiteRequest request = new EditWaterSiteRequest(UUID.randomUUID(),"",0,0);
        EditWaterSiteResponse response = waterSiteServices.editWaterSite(request);
        assertNotNull(response);
        assertEquals("Watersite does not exist.", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Attempt to edit a water site with success")
    public void EditSiteSuccess(){
        //setup
        Mockito.when(repo.findById(Mockito.any())).thenReturn(Optional.of(new WaterSite()));

        //test
        EditWaterSiteRequest request = new EditWaterSiteRequest(UUID.randomUUID(),"Apple",12,15);
        EditWaterSiteResponse response = waterSiteServices.editWaterSite(request);
        assertNotNull(response);
        assertEquals("Watersite successfully edited.", response.getStatus());
        assertEquals(true, response.getSuccess());
    }
}
