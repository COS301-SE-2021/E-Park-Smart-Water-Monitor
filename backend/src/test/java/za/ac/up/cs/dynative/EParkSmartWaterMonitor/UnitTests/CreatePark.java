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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.WeatherData;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.CreateParkRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.CreateParkResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CreatePark {


    @Mock
    private ParkRepo parkRepo;

    @InjectMocks
    private ParkServiceImpl parkService;

    Double lat =-27.371767;
    Double lon =28.737437;

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
    @DisplayName("Attempt to create a park but the park name is unknown")
    public void createParkNameNull() {
        CreateParkRequest request= new CreateParkRequest("",lat,lon);
        CreateParkResponse response = parkService.createPark(request);
        assertNotNull(response);
        assertEquals("No park name specified!",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Creating a new park successfully")
    public void createParkSuccess() {
        //setup
        Mockito.when(parkRepo.findParkByParkName("Unit Testing")).thenReturn(new ArrayList<>());

        //test
        CreateParkRequest request= new CreateParkRequest("Unit Testing",lat,lon);
        assertNotNull(request);
        CreateParkResponse response= parkService.createPark(request);
        assertNotNull(response);
        assertEquals("Park "+request.getParkName()+" Added!",response.getStatus());
        assertEquals(true,response.getSuccess());
    }

    @Test
    @DisplayName("Creating a park with an existing name")
    public void createParkAlreadyExists()  {
        //setup
        site1= new WaterSite(id1,name1,lat1,lon1,shape,length,width,radius);
        site2= new WaterSite(id2,name2,lat2,lon2,shape,length,width,radius);
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
        List<Park> list= new ArrayList<>();
        list.add(park1);
        list.add(park2);
        List<Park> p =new ArrayList<>();
        p.add(park2);
        Mockito.when(parkRepo.findParkByParkName("Unit Test 2")).thenReturn(p);

        //test
        CreateParkRequest request= new CreateParkRequest("Unit Test 2",lat,lon);
        CreateParkResponse response= parkService.createPark(request);
        assertEquals("Park Unit Test 2 already exists!",response.getStatus());
        assertEquals(false,response.getSuccess());

    }

}
