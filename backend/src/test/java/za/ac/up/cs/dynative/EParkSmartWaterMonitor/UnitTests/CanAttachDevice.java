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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.CanAttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.CanAttachWaterSourceDeviceResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CanAttachDevice {
    @Mock
    WaterSiteRepo repo;

    @InjectMocks
    WaterSiteServicesImpl waterSiteServices;

    WaterSite site1;
    UUID id1= UUID.randomUUID();
    double lat1= -27.371767;
    double lon1=28.737437;
    String name1="UnitTest 1";

    @Test
    @DisplayName("Test if a device can be added to a site, but the site id is null")
    public void CanAttachIDNULL(){
        CanAttachWaterSourceDeviceRequest request= new CanAttachWaterSourceDeviceRequest(null);
        Throwable t= assertThrows(InvalidRequestException.class,()->waterSiteServices.canAttachWaterSourceDevice(request));
        assertEquals("No id specified",t.getMessage());
    }


    @Test
    @DisplayName("Test if a device can be added to a site, but the request is null")
    public void CanAttachRequestNull(){
        Throwable t= assertThrows(InvalidRequestException.class,()->waterSiteServices.canAttachWaterSourceDevice(null));
        assertEquals("Request is null",t.getMessage());
    }

    @Test
    @DisplayName("Test if a device can be added to a site, but the site id does not exist")
    public void CanAttachDNE(){
        Optional<WaterSite> op= Optional.empty();
        Mockito.when(repo.findById(id1)).thenReturn(op);

        CanAttachWaterSourceDeviceRequest request= new CanAttachWaterSourceDeviceRequest(id1);
        Throwable t= assertThrows(InvalidRequestException.class,()->waterSiteServices.canAttachWaterSourceDevice(request));
        assertEquals("Site does not exist",t.getMessage());
    }

    @Test
    @DisplayName("Test if a device can be added to a site successful")
    public void CanAttach() throws InvalidRequestException {
        site1= new WaterSite(id1,name1,lat1,lon1);
        Optional<WaterSite> op = Optional.of(site1);
        Mockito.when(repo.findById(id1)).thenReturn(op);

        CanAttachWaterSourceDeviceRequest request= new CanAttachWaterSourceDeviceRequest(id1);
        CanAttachWaterSourceDeviceResponse response= waterSiteServices.canAttachWaterSourceDevice(request);
        assertNotNull(response);
        assertEquals("Can attach device to site!",response.getStatus());
        assertEquals(true,response.getSuccess());
    }
}
