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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetAllParksResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetAllParks {
    @Mock
    private ParkRepo parkRepo;

    @InjectMocks
    private ParkServiceImpl parkService;

    WaterSite site1;
    WaterSite site2;
    WaterSite site3;
    UUID id1= UUID.randomUUID();
    UUID id2= UUID.randomUUID();
    UUID id3= UUID.randomUUID();
    double lat1= -27.371767;
    double lat2= -27.991767;
    double lat3= -27.771787;
    double lon1=28.737437;
    double lon2=28.007437;
    double lon3=28.555437;
    String shape="circle";
    double length = 0;
    double width=0;
    double radius=1.785;
    String name1="UnitTest 1";
    String name2="UnitTest 2";
    String name3="UnitTest 3";
    Set<WaterSite> siteSet1;
    Set<WaterSite> siteSet2;
    Set<WaterSite> siteSet3;

    int id21= 336;
    int id22= 337;
    int id23= 338;
    WeatherData weather1;
    WeatherData weather2;
    WeatherData weather3;
    double temp1=28;
    double temp2=28;
    double temp3=30.1;
    String moonPhase1 = "Growing";
    String moonPhase2 = "Full";
    String moonPhase3 = "Shrinking";
    double humudity1= 39;
    double humudity2= 37;
    double humudity3= 39.6;
    double windSpeed1 =16;
    double windSpeed2 =20;
    double windSpeed3 =30;
    Date date1= new Date();
    Date date2= new Date();
    Date date3= new Date();
    Set<WeatherData> weatherSet1;
    Set<WeatherData> weatherSet2;
    Set<WeatherData> weatherSet3;


    Park park1;
    Park park2;
    Park park3;

    @Test
    @DisplayName("Find all the existing parks")
    public void getAllParksExisting() throws InvalidRequestException {
        //setting up the repo
        site1= new WaterSite(id1,name1,lat1,lon1,shape,length,width,radius);
        site2= new WaterSite(id2,name2,lat2,lon2,shape,length,width,radius);
        site3= new WaterSite(id3,name3,lat3,lon3,shape,length,width,radius);
        siteSet1= new HashSet<>();
        siteSet2= new HashSet<>();
        siteSet3= new HashSet<>();
        siteSet1.add(site1);
        siteSet2.add(site2);
        siteSet3.add(site3);

        weather1= new WeatherData(id21,temp1,moonPhase1,humudity1,windSpeed1,date1);
        weather2= new WeatherData(id22,temp2,moonPhase2,humudity2,windSpeed2,date2);
        weather3= new WeatherData(id23,temp3,moonPhase3,humudity3,windSpeed3,date3);
        weatherSet1 = new HashSet<>();
        weatherSet2 = new HashSet<>();
        weatherSet3 = new HashSet<>();
        weatherSet1.add(weather1);
        weatherSet2.add(weather2);
        weatherSet3.add(weather3);

        park1= new Park("Unit Test Park 1",-27.378888,28.111471,weatherSet1,siteSet1);
        park2= new Park("Unit Test Park 2",-27.368888,28.681111,weatherSet2,siteSet2);
        park3= new Park("Unit Test Park 3",-27.379988,28.118989,weatherSet3,siteSet3);
        List<Park> list= new ArrayList<>();
        list.add(park1);
        list.add(park2);
        list.add(park3);
        Mockito.when(parkRepo.getAllParks()).thenReturn(list);

        //testing
        GetAllParksResponse response= parkService.getAllParks();
        assertNotNull(response);
        assertEquals(list,response.getAllParks());
        assertEquals(3,response.getAllParks().size());
        assertEquals(3,response.getAllParks().size());
        assertEquals("Unit Test Park 1",response.getAllParks().get(0).getParkName());
        assertEquals(-27.368888,response.getAllParks().get(1).getLatitude());
        assertEquals(28.118989,response.getAllParks().get(2).getLongitude());
    }

    @Test
    @DisplayName("Find all parks, but list is empty")
    public void getAllParksEmpty(){
        //repo setup
        Mockito.when(parkRepo.getAllParks()).thenReturn(null);

        //testing
        GetAllParksResponse response= parkService.getAllParks();
        assertNotNull(response);
        assertEquals(null,response.getAllParks());
    }
}
