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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AddWaterSite {
    @Mock
    WaterSiteRepo repo;

    @Mock(name="parkService")
    ParkServiceImpl parkService;

    @InjectMocks
    WaterSiteServicesImpl waterSiteServices;

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
    @DisplayName("Attempt to add a water site but the request is null")
    public void AddSiteRequestNull(){
        AddSiteResponse response = waterSiteServices.addSite(null);
        assertNotNull(response);
        assertEquals("Request is null", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Attempt to add a water site but no id is specified")
    public void AddSiteIDNull(){
        AddSiteRequest request= new AddSiteRequest(null,name1,lat1,lon1,shape,length,width,radius);
        AddSiteResponse response = waterSiteServices.addSite(request);
        assertNotNull(response);
        assertEquals("No park id specified", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Attempt to add a water site but the park does not exist")
    public void AddSiteParkDNE() {
        //setup
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(null);

        //test
        AddSiteRequest request= new AddSiteRequest(id2,name1,lat1,lon1,shape,length,width,radius);
        AddSiteResponse response = waterSiteServices.addSite(request);
        assertNotNull(response);
        assertEquals("Park not found", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Add a water site to a park successfully")
    public void AddSiteSuccess()  {
        //setup
        Park p= new Park();
        FindByParkIdResponse response =new FindByParkIdResponse(true,p);
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(response);

        //test
        AddSiteRequest request=new AddSiteRequest(id2,name2,lat2,lon2,shape,length,width,radius);
        AddSiteResponse response2= waterSiteServices.addSite(request);
        assertNotNull(response);
        assertEquals("Successfully added: " + request.getSiteName(),response2.getStatus());
        assertEquals(true,response2.getSuccess());
    }
}
