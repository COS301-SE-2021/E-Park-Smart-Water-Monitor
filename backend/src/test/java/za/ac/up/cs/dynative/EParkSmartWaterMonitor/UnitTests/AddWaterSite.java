package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AddSiteRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AddSiteResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AddWaterSite {
    @Mock
    WaterSiteRepo repo;

    @Mock(name="parkService")
    ParkServiceImpl parkService;

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
    String name1="UnitTest 1";
    String name2="UnitTest 2";

    @Test
    @DisplayName("Add a water site but the request is null")
    public void AddSiteRequestNull(){
        Throwable t= assertThrows(InvalidRequestException.class,()->waterSiteServices.addSite(null));
        assertEquals("Request is null",t.getMessage());
    }

    @Test
    @DisplayName("Add a water site but the park id is null")
    public void AddSiteIDNull(){
        AddSiteRequest request= new AddSiteRequest(null,name1,lat1,lon1);
        Throwable t= assertThrows(InvalidRequestException.class,()->waterSiteServices.addSite(request));
        assertEquals("No park id specified",t.getMessage());
    }

    @Test
    @DisplayName("Add a water site but the park does not exist")
    public void AddSiteDNE() throws InvalidRequestException {
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(null);

        AddSiteRequest request2= new AddSiteRequest(id2,name1,lat1,lon1);
        Throwable t= assertThrows(InvalidRequestException.class,()->waterSiteServices.addSite(request2));
        assertEquals("Park not found",t.getMessage());
    }

    @Test
    @DisplayName("Add a water site to a park")
    public void AddSite() throws InvalidRequestException {
        Park p= new Park();
        FindByParkIdResponse response =new FindByParkIdResponse(true,p);
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(response);

        AddSiteRequest request2=new AddSiteRequest(id2,name2,lat2,lon2);
        AddSiteResponse response2= waterSiteServices.addSite(request2);
        assertNotNull(response2);
        assertEquals("Successfully added: " + request2.getSiteName(),response2.getStatus());
        assertEquals(true,response2.getSuccess());
    }
}
