package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.InfrastructureDeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.InfrastructureRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.WaterSourceDeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class GetDeviceData {
    @Mock
    private WaterSourceDeviceRepo waterSourceDeviceRepo;
    @Mock
    private InfrastructureDeviceRepo infrastructureDeviceRepo;
    @Mock
    private InfrastructureRepo infrastructureRepo;
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
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.getDeviceData(new GetDeviceDataRequest("",23)));
        assertEquals("Device name not specified",t.getMessage());

        Throwable t2= assertThrows(InvalidRequestException.class,()->devicesServices.getDeviceData(new GetDeviceDataRequest("",9)));
        assertEquals("Device name not specified",t2.getMessage());
    }

    @Test
    @DisplayName("Device not found when searched")
    public void getDeviceDataDNE(){
        Mockito.when(waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(new ArrayList<>());

        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.getDeviceData(new GetDeviceDataRequest("test",23)));
        assertEquals("Device does not exist",t.getMessage());
    }

    //TODO: Fix application.properties for AWS credentials and uncomment when done.
//    @Test
//    @DisplayName("Device found when searched")
//    public void getDeviceData() throws InvalidRequestException {
//        List<WaterSourceDevice> device= new ArrayList<>();
//        device.add(new WaterSourceDevice());
//        Mockito.when(waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(device);
//
//        GetDeviceDataRequest request= new GetDeviceDataRequest("test",22);
//        GetDeviceDataResponse response= devicesServices.getDeviceData(request);
//        assertNotNull(response);
//        assertEquals(true,response.getSuccess());
//        assertEquals("test",response.getDeviceName());
//        assertEquals("Successfully retrieved data for device: test",response.getStatus());
//    }
}
