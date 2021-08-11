package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetParkDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetParkDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetParkDevices {
    @Mock
    private DeviceRepo deviceRepo;
    @Mock
    private MeasurementRepo measurementRepo;

    @Mock(name = "parkService")
    private ParkServiceImpl parkService;
    @Mock(name = "waterSiteService")
    private WaterSiteServicesImpl waterSiteServices;

    @InjectMocks
    private DevicesServicesImpl devicesServices;

    @Test
    @DisplayName("Request to get park devices is null")
    public void getDevicesRequestNull(){
        GetParkDevicesResponse response= devicesServices.getParkDevices(null);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals(null,response.getSite());
        assertEquals("Request is null",response.getStatus());
    }

    @Test
    @DisplayName("Request to get the devices in a park but the prk id is null")
    public void getDevicesParkIDNull(){
        GetParkDevicesResponse response= devicesServices.getParkDevices(new GetParkDevicesRequest(null));
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals(null,response.getSite());
        assertEquals("No Park ID specified",response.getStatus());
    }

    @Test
    @DisplayName("Request to get the devices of a park, but there is no devices")
    public void getDevicesDNE() {
        //setup
        UUID id= UUID.randomUUID();
        Mockito.when(deviceRepo.findAll()).thenReturn(null);

        //test
        GetParkDevicesRequest request= new GetParkDevicesRequest(id);
        GetParkDevicesResponse response= devicesServices.getParkDevices(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals(null,response.getSite());
        assertEquals("No devices present",response.getStatus());
    }

    @Test
    @DisplayName("Get the num devices of a park successfully")
    public void getNumDevices()  {
        UUID id= UUID.randomUUID();
        List<Device> devices= new ArrayList<>();
        devices.add(new Device());
        devices.add(new Device());
        devices.add(new Device());
        Mockito.when(deviceRepo.findAll()).thenReturn(devices);

        GetParkDevicesRequest request= new GetParkDevicesRequest(id);
        GetParkDevicesResponse response1= devicesServices.getParkDevices(request);
        assertNotNull(response1);
        assertEquals(true,response1.getSuccess());
        assertEquals(devices,response1.getSite());
        assertEquals("Successfully got the Park's devices",response1.getStatus());
    }
}
