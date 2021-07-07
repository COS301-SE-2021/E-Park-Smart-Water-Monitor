package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.requests.AttachWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.AttachWaterSourceDeviceResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AttachDevice {

    @Mock
    WaterSiteRepo repo;

    @InjectMocks
    WaterSiteServicesImpl waterSiteServices;

    UUID id1= UUID.randomUUID();
    UUID id2= UUID.randomUUID();
    double lat1= -27.371767;
    double lat2= -27.991767;
    double lon1=28.737437;
    double lon2=28.007437;
    String name1="UnitTest 1";

    @Test
    @DisplayName("Try and attach a water source device but the site has a null id")
    public void AttachNull(){
        Device device = new Device(UUID.randomUUID(),"TEST!!!","WaterSource", "UNIT",3,9);
        AttachWaterSourceDeviceRequest request= new AttachWaterSourceDeviceRequest(null,device);
        Throwable t =assertThrows(InvalidRequestException.class, ()->waterSiteServices.attachWaterSourceDevice(request));
        assertEquals("No Id specified",t.getMessage());
    }

    @Test
    @DisplayName("Try and attach a water source device but the site does not exist")
    public void AttachDNE(){
        UUID test= UUID.randomUUID();
        Optional<WaterSite> op= Optional.empty();
        Mockito.when(repo.findById(test)).thenReturn(op);
        Device device = new Device(UUID.randomUUID(),"TEST!!!","WaterSource", "UNIT",3,9);

        AttachWaterSourceDeviceRequest request= new AttachWaterSourceDeviceRequest(test,device);
        Throwable t =assertThrows(InvalidRequestException.class, ()->waterSiteServices.attachWaterSourceDevice(request));
        assertEquals("Site not found",t.getMessage());
    }

    @Test
    @DisplayName("Succesfully attach a device")
    public void Attach() throws InvalidRequestException {
        WaterSite site1= new WaterSite(id1,name1,lat1,lon1);
        Device device = new Device(id2,"TESTING","UNIt","WaterSource",lat2,lon2);
        Optional<WaterSite> op = Optional.of(site1);
        Mockito.when(repo.findById(id1)).thenReturn(op);

        AttachWaterSourceDeviceRequest request= new AttachWaterSourceDeviceRequest(id1,device);
        AttachWaterSourceDeviceResponse response= waterSiteServices.attachWaterSourceDevice(request);
        assertNotNull(response);
        assertEquals("Successfully attached device to site!",response.getStatus());
        assertEquals(true,response.getSuccess());
    }

    @Test
    @DisplayName("Try and attach a water source device but the device has a null id")
    public void AttachNullDeviceID(){
        Device device = new Device(null,"TEST!!!", "UNIT","WaterSource",3,9);
        AttachWaterSourceDeviceRequest request= new AttachWaterSourceDeviceRequest(id1,device);
        Throwable t =assertThrows(InvalidRequestException.class, ()->waterSiteServices.attachWaterSourceDevice(request));
        assertEquals("No device Id specified",t.getMessage());
    }

    @Test
    @DisplayName("Try and attach a water source device but the device is null")
    public void AttachDNEDevice(){
        AttachWaterSourceDeviceRequest request= new AttachWaterSourceDeviceRequest(UUID.randomUUID(),null);
        Throwable t =assertThrows(InvalidRequestException.class, ()->waterSiteServices.attachWaterSourceDevice(request));
        assertEquals("No device specified",t.getMessage());
    }



}
