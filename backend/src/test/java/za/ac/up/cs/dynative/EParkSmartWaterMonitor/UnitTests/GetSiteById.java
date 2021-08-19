package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.GetSiteByIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.GetSiteByIdResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetSiteById {

    @Mock
    WaterSiteRepo repo;

    @InjectMocks
    WaterSiteServicesImpl waterSiteServices;

    WaterSite site1;
    WaterSite site2;
    UUID id1= UUID.randomUUID();
    UUID id2= UUID.randomUUID();
    double lat1= -27.371767;
    double lat2= -27.991767;
    double lon1=28.737437;
    double lon2=28.007437;
    String shape="circle";
    double length = 0;
    double width=0;
    double radius=1.785;
    String name1="UnitTest 1";
    String name2="UnitTest 2";

    @Test
    @DisplayName("Find a site but the id is null")
    public void FindSiteIdNull(){
        GetSiteByIdRequest request = new GetSiteByIdRequest(null);
        GetSiteByIdResponse response= waterSiteServices.getSiteById(request);
        assertNotNull(response);
        assertEquals("No id specified",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Find a site with an invalid id")
    public void FindSiteDNE(){
        //setup
        UUID test =UUID.randomUUID();
        Optional<WaterSite> op = Optional.empty();
        Mockito.when(repo.findById(test)).thenReturn(op);

        //test
        GetSiteByIdRequest request = new GetSiteByIdRequest(test);
        GetSiteByIdResponse response= waterSiteServices.getSiteById(request);
        assertNotNull(response);
        assertEquals("Site does not exist.",response.getStatus());
        assertEquals(false,response.getSuccess());

    }

    @Test
    @DisplayName("Find a site with a valid id")
    public void FindSiteWithID() {
        //setup
        site1= new WaterSite(id1,name1,lat1,lon1);
        site2= new WaterSite(id2,name2,lat2,lon2);
        Optional<WaterSite> foundSite = Optional.of(site1);
        Mockito.when(repo.findById(site1.getId())).thenReturn(foundSite);

        //test
        GetSiteByIdRequest request = new GetSiteByIdRequest(site1.getId());
        GetSiteByIdResponse response= waterSiteServices.getSiteById(request);
        assertNotNull(response);
        assertEquals(response.getSite(),site1);
        assertEquals("Successfully found site.",response.getStatus());
        assertEquals(true,response.getSuccess());
    }
}
