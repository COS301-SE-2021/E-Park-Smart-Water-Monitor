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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkNameRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkNameResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FindParkByName {
    @Mock
    private ParkRepo parkRepo;

    @InjectMocks
    private ParkServiceImpl parkService;

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
    Set<WaterSite> siteSet1;
    Set<WaterSite> siteSet2;
    int id21= 336;
    int id22= 337;
    WeatherData weather1;
    WeatherData weather2;
    double temp1=28;
    double temp2=28;
    String moonPhase1 = "Growing";
    String moonPhase2 = "Full";
    double humudity1= 39;
    double humudity2= 37;
    double windSpeed1 =16;
    double windSpeed2 =20;
    Date date1= new Date();
    Date date2= new Date();
    Set<WeatherData> weatherSet1;
    Set<WeatherData> weatherSet2;
    Park park1;
    Park park2;

    @Test
    @DisplayName("Find a park by name, but the request is null")
    public void findParkNull(){
        FindByParkNameResponse response =parkService.findParkByName(null);
        assertNotNull(response);
        assertEquals(null, response.getPark());
    }

    @Test
    @DisplayName("Find a park by name, but name is null")
    public void findParkNoName(){
        FindByParkNameRequest request= new FindByParkNameRequest("");
        FindByParkNameResponse response =parkService.findParkByName(request);
        assertNotNull(response);
        assertEquals(null, response.getPark());
    }

    @Test
    @DisplayName("Find a non existing park")
    public void findParkDNE(){
        //setup
        Mockito.when(parkRepo.findParkByParkName("DNE")).thenReturn(null);

        //test
        FindByParkNameRequest request= new FindByParkNameRequest("DNE");
        FindByParkNameResponse response =parkService.findParkByName(request);
        assertNotNull(response);
        assertEquals(null, response.getPark());
    }

    @Test
    @DisplayName("Find an existing park.")
    public void FindParkExisting() throws InvalidRequestException {
        site1= new WaterSite(id1,name1,lat1,lon1);
        site2= new WaterSite(id2,name2,lat2,lon2);
        siteSet1= new HashSet<>();
        siteSet2= new HashSet<>();
        siteSet1.add(site1);
        siteSet2.add(site2);

        weather1= new WeatherData(id21,temp1,moonPhase1,humudity1,windSpeed1,date1);
        weather2= new WeatherData(id22,temp2,moonPhase2,humudity2,windSpeed2,date2);
        weatherSet1 = new HashSet<>();
        weatherSet2 = new HashSet<>();
        weatherSet1.add(weather1);
        weatherSet2.add(weather2);

        park1= new Park("Unit Test Park 1",-27.378888,28.111471,weatherSet1,siteSet1);
        park2= new Park("Unit Test Park 2",-27.368888,28.681111,weatherSet2,siteSet2);
        List<Park> p= new ArrayList<>();
        p.add(park2);
        Mockito.when(parkRepo.findParkByParkName(park2.getParkName())).thenReturn(p);

        FindByParkNameRequest request= new FindByParkNameRequest("Unit Test Park 2");
        FindByParkNameResponse response= parkService.findParkByName(request);
        assertNotNull(response);
        assertEquals("Unit Test Park 2", response.getPark().getParkName());
        assertEquals(park2.getLatitude(),response.getPark().getLatitude());
        assertEquals(park2.getLongitude(),response.getPark().getLongitude());
        assertEquals(park2.getParkWeather(),response.getPark().getParkWeather());
        assertEquals(park2.getParkWaterSites(),response.getPark().getParkWaterSites());
    }
}
