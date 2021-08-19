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
    @DisplayName("Request is null")
    public void getDeviceDataRequestNull(){
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.getDeviceData(null));
        assertEquals("Request is null",t.getMessage());
    }

    @Test
    @DisplayName("No device name sppecified when trying to get data")
    public void getDeviceDataNameNull(){
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.getDeviceData(new GetDeviceDataRequest("",23, false)));
        assertEquals("Device name not specified",t.getMessage());

        Throwable t2= assertThrows(InvalidRequestException.class,()->devicesServices.getDeviceData(new GetDeviceDataRequest("",9, false)));
        assertEquals("Device name not specified",t2.getMessage());
    }

    @Test
    @DisplayName("Device not found when searched")
    public void getDeviceDataDNE(){
        Mockito.when(deviceRepo.findWaterSourceDeviceByDeviceName(Mockito.any())).thenReturn(new ArrayList<Device>());

        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.getDeviceData(new GetDeviceDataRequest("test",23, false)));
        assertEquals("Device does not exist",t.getMessage());
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
