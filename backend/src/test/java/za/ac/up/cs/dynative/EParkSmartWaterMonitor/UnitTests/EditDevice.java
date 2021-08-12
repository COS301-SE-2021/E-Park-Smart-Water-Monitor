package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.EditDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.EditDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EditDevice {

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
    @DisplayName("Attempt to edit a device but the request is null")
    public void EditDevicesRequestNull() {
        EditDeviceResponse response = devicesServices.editDevice(null);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request is null",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to edit a device but the device is the wrong type")
    public void EditDevicesWrongType() {
        EditDeviceRequest request = new EditDeviceRequest(UUID.randomUUID(),"abc", "xyz", "abc");
        EditDeviceResponse response = devicesServices.editDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("The specified device type abc does not exist.",response.getStatus());
    }


    @Test
    @DisplayName("Attempt to edit a device but the request is incomplete")
    public void EditDeviceIncompleteRequest(){
        EditDeviceRequest request = new EditDeviceRequest(UUID.randomUUID(),"", "xyz", "abc");
        EditDeviceResponse response = devicesServices.editDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request incomplete",response.getStatus());
    }
}
