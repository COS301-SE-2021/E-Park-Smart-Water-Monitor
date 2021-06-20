package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceDevice;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.InfrastructureDeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.InfrastructureRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.WaterSourceDeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetParkDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetParkDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class GetParkDevices {
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
    public void getDevicesRequestNull(){
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.getParkDevices(null));
        assertEquals("Request is null",t.getMessage());
    }

    @Test
    @DisplayName("No park id specified")
    public void getDevicesParkIDNull(){
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.getParkDevices(new GetParkDevicesRequest(null)));
        assertEquals("Park id not specified",t.getMessage());
    }

    @Test
    @DisplayName("Try to get the devices but there are no devices")
    public void getDevicesDNE() throws InvalidRequestException {
        UUID id= UUID.randomUUID();
        Mockito.when(waterSourceDeviceRepo.findAll()).thenReturn(null);

        GetParkDevicesRequest request= new GetParkDevicesRequest(id);
        Throwable t= assertThrows(InvalidRequestException.class,()->devicesServices.getParkDevices(request));
        assertEquals("No devices present",t.getMessage());
    }

    @Test
    @DisplayName("Get the num devices of a park")
    public void getNumDevices() throws InvalidRequestException {
        UUID id= UUID.randomUUID();
        List<WaterSourceDevice> devices= new ArrayList<>();
        devices.add(new WaterSourceDevice());
        devices.add(new WaterSourceDevice());
        devices.add(new WaterSourceDevice());
        Mockito.when(waterSourceDeviceRepo.findAll()).thenReturn(devices);

        GetParkDevicesRequest request= new GetParkDevicesRequest(id);
        GetParkDevicesResponse response1= devicesServices.getParkDevices(request);
        assertNotNull(response1);
        assertEquals(true,response1.getSuccess());
        assertEquals(devices,response1.getSite());
        assertEquals("Successfully got the Park's devices",response1.getStatus());
    }
}
