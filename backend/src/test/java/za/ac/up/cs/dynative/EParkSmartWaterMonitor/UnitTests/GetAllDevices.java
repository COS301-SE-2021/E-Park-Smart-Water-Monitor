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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetAllDevices {

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
    @DisplayName("Try to get all the devices in the system but no devices are found")
    public void getAllDevicesFail() {
        List<Device> devices = new ArrayList<>();
        Mockito.when(deviceRepo.findAll()).thenReturn(devices);

        GetAllDevicesResponse response = devicesServices.getAllDevices();
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to get all the devices",response.getStatus());
    }

    @Test
    @DisplayName("Return all devices in the system")
    public void getAllDevicesSuccess() {
        List<Device> devices = new ArrayList<>();
        Device d = new Device();
        devices.add(d);
        Mockito.when(deviceRepo.findAll()).thenReturn(devices);

        GetAllDevicesResponse response = devicesServices.getAllDevices();
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Successfully got all the devices",response.getStatus());
    }
}
