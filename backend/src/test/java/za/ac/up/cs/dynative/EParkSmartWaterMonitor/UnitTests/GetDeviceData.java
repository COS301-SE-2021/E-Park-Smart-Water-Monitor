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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetDeviceData {
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
    @DisplayName("Request to get device data is null")
    public void getDeviceDataRequestNull(){
        GetDeviceDataResponse response= devicesServices.getDeviceData(null);
        assertNotNull(response);
        assertEquals("Request is null", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Try to get device data but the device name is null")
    public void getDeviceDataNameNull(){
        GetDeviceDataResponse response= devicesServices.getDeviceData(new GetDeviceDataRequest("",23));
        assertNotNull(response);
        assertEquals("No device name is specified", response.getStatus());
        assertEquals(false, response.getSuccess());
    }

    @Test
    @DisplayName("Device not found when searched for")
    public void getDeviceDataNotFound(){
        //setup
        Mockito.when(deviceRepo.findWaterSourceDeviceByDeviceName(Mockito.any())).thenReturn(new ArrayList<Device>());

        //test
        GetDeviceDataResponse response= devicesServices.getDeviceData(new GetDeviceDataRequest("test",23));
        assertNotNull(response);
        assertEquals(false, response.getSuccess());
        assertEquals("Device does not exist",response.getStatus());
    }


    /*@Test
    @DisplayName("Device found when searched")
    public void getDeviceData() throws InvalidRequestException {
        List<WaterSourceDevice> device= new ArrayList<>();
        device.add(new WaterSourceDevice());
        Mockito.when(waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName(Mockito.any())).thenReturn(device);

        GetDeviceDataRequest request= new GetDeviceDataRequest("Water12",3);
        GetDeviceDataResponse response= devicesServices.getDeviceData(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("test",response.getDeviceName());
        assertEquals("Successfully retrieved data for device: test",response.getStatus());
    }*/
}
