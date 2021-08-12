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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.EditDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.EditDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        //test 1:
        EditDeviceRequest request = new EditDeviceRequest(UUID.randomUUID(),"", "xyz", "abc");
        EditDeviceResponse response = devicesServices.editDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request incomplete",response.getStatus());

        //test 2:
        request = new EditDeviceRequest(null,"568", "xyz", "abc");
        response = devicesServices.editDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request incomplete",response.getStatus());

        //test 3:
        request = new EditDeviceRequest(null,"568", "", "");
        response = devicesServices.editDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request incomplete",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to edit a device but the device is not present")
    public void EditDeviceNotPresent(){
        //setup
        UUID id=UUID.randomUUID();
        EditDeviceRequest request = new EditDeviceRequest(id, "WaterSource", "a", "P12Q");
        Optional<Device> device = Optional.empty();
        Mockito.when(deviceRepo.findById(id)).thenReturn(device);

        //test
        EditDeviceResponse response = devicesServices.editDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("That device does not exist.",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to edit a device but the new name is a duplicate")
    public void EditDeviceDuplicateName(){
        //setup
        UUID id=UUID.randomUUID();
        EditDeviceRequest request = new EditDeviceRequest(id, "WaterSource", "a", "P12Q");
        Device d = new Device();
        d.setDeviceName("abc");
        Optional<Device> device = Optional.of(d);
        List<Device> devicesReturn = new ArrayList<>();
        devicesReturn.add(d);
        Mockito.when(deviceRepo.findById(id)).thenReturn(device);
        Mockito.when(deviceRepo.findDeviceByDeviceName(Mockito.any())).thenReturn(devicesReturn);

        //test
        EditDeviceResponse response = devicesServices.editDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("A device with that name already exists",response.getStatus());
    }

}
