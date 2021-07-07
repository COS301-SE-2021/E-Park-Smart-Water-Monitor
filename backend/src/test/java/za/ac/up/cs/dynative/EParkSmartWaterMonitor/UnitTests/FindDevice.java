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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.WaterSourceDeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.FindDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FindDevice {
    @Mock
    private WaterSourceDeviceRepo waterSourceDeviceRepo;
    @Mock
    private MeasurementRepo measurementRepo;


    @Mock(name = "parkService")
    private ParkServiceImpl parkService;
    @Mock(name = "waterSiteService")
    private WaterSiteServicesImpl waterSiteServices;

    @InjectMocks
    private DevicesServicesImpl devicesServices;

    @Test
    @DisplayName("find Device with null request")
    public void findDeviceNullRequest(){
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.findDevice(null));
        assertEquals("Request is null",t.getMessage());
    }

    @Test
    @DisplayName("find Device with null id")
    public void findDeviceNullID(){
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.findDevice(new FindDeviceRequest(null)));
        assertEquals("No id specified",t.getMessage());
    }

    @Test
    @DisplayName("find Device that does not exist")
    public void findDeviceDNE(){
        UUID test= UUID.randomUUID();
        Optional<WaterSourceDevice> op= Optional.empty();
        Mockito.when(waterSourceDeviceRepo.findById(test)).thenReturn(op);

        FindDeviceRequest request= new FindDeviceRequest(test);
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.findDevice(request));
        assertEquals("Device not found",t.getMessage());
    }

    @Test
    @DisplayName("device found")
    public void findDevice() throws InvalidRequestException {
        UUID test= UUID.randomUUID();
        WaterSourceDevice device= new WaterSourceDevice();
        Optional<WaterSourceDevice> op= Optional.of(device);
        Mockito.when(waterSourceDeviceRepo.findById(test)).thenReturn(op);

        FindDeviceRequest request= new FindDeviceRequest(test);
        FindDeviceResponse response= devicesServices.findDevice(request);
        assertNotNull(response);
        assertEquals("Device found",response.getStatus());
        assertEquals(true,response.getSuccess());
        assertEquals(device,response.getDevice());
    }
}
