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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.DeleteDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.DeleteDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DeleteDevice {

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
    @DisplayName("Attempt to delete a device but the id is null")
    public void DeleteDeviceNullID() {
        DeleteDeviceResponse response = devicesServices.deleteDevice(new DeleteDeviceRequest(null));
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("No device id specified.",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to delete a device but the device does not exist")
    public void DeleteDeviceDeviceDNE() {
        //setup
        Optional<Device> device = Optional.empty();
        Mockito.when(deviceRepo.findById(Mockito.any())).thenReturn(device);

        //test
        DeleteDeviceResponse response = devicesServices.deleteDevice(new DeleteDeviceRequest(UUID.randomUUID()));
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("No device with this id exists.",response.getStatus());
    }

    @Test
    @DisplayName("Successfully delete the device")
    public void DeleteDeviceSuccess() {
        //setup
        Device d = new Device();
        d.setDeviceName("testing");
        Optional<Device> device = Optional.of(d);
        Mockito.when(deviceRepo.findById(Mockito.any())).thenReturn(device);

        //test
        DeleteDeviceResponse response = devicesServices.deleteDevice(new DeleteDeviceRequest(UUID.randomUUID()));
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Successfully deleted the device and all related entities.",response.getStatus());
    }
}
