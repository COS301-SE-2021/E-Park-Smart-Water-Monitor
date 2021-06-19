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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.WeatherData;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.GetParkSitesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.GetParkSitesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class GetParkSites {
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
    String name1="UnitTest 1";
    String name2="UnitTest 2";
    String name3="UnitTest 3";
    Set<WaterSite> site;

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
    Set<WeatherData> weather;


    Park park1;


    @Test
    @DisplayName("Find all the sites of an existing park.")
    public void getSitesExisting() throws InvalidRequestException {
        //setting up the repo
        site1= new WaterSite(id1,name1,lat1,lon1);
        site2= new WaterSite(id2,name2,lat2,lon2);
        site3= new WaterSite(id3,name3,lat3,lon3);
        site= new HashSet<>();
        site.add(site1);
        site.add(site2);
        site.add(site3);

        weather1= new WeatherData(id21,temp1,moonPhase1,humudity1,windSpeed1,date1);
        weather2= new WeatherData(id22,temp2,moonPhase2,humudity2,windSpeed2,date2);
        weather3= new WeatherData(id23,temp3,moonPhase3,humudity3,windSpeed3,date3);
        weather = new HashSet<>();
        weather.add(weather1);
        weather.add(weather2);
        weather.add(weather3);

        park1= new Park("Unit Test Park",-27.378888,28.111111,weather,site);
        Mockito.when(parkRepo.findParkById(park1.getId())).thenReturn(park1);

        //testing
        GetParkSitesRequest request= new GetParkSitesRequest(park1.getId());
        assertNotNull(request);
        GetParkSitesResponse response= parkService.getParkWaterSites(request);
        assertNotNull(response);
        assertEquals(3,response.getSite().size());
        assertEquals("Park Sites and their IoT devices",response.getStatus());
        assertEquals(true,response.getSuccess());
        assertEquals(site,response.getSite());
    }


    @Test
    @DisplayName("Find all the sites of an non existing park.")
    public void getSitesDNE(){
        //setting up the repo
        UUID testID= UUID.randomUUID();
        Mockito.when(parkRepo.findParkById(testID)).thenReturn(null);

        //testing
        GetParkSitesRequest request= new GetParkSitesRequest(testID);
        Throwable t= assertThrows(InvalidRequestException.class,()->parkService.getParkWaterSites(request));
        assertEquals("Park not present",t.getMessage());

    }

    @Test
    @DisplayName("Find all the sites of a park but no ID is provided.")
    public void getSitesNull(){
        GetParkSitesRequest request= new GetParkSitesRequest(null);
        Throwable t= assertThrows(InvalidRequestException.class,()->parkService.getParkWaterSites(request));
        assertEquals("No ID provided",t.getMessage());

    }
}
