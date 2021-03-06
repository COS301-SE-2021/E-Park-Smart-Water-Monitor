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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.GetNumDevicesRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetNumDevicesResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetNumDevices {
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
    @DisplayName("Try to get the num devices when the request is null")
    public void getNumDevicesRequestNull() throws InvalidRequestException {
        GetNumDevicesResponse response= devicesServices.getNumDevices(null);
        assertNotNull(response);
        assertEquals(false,response.isSuccess());
        assertEquals(-1,response.getNumDevices());
    }

    @Test
    @DisplayName("Try to get the num devices at a park but the park id is null.")
    public void getNumDevicesParkIDNull() throws InvalidRequestException {
        GetNumDevicesResponse response= devicesServices.getNumDevices(new GetNumDevicesRequest(null));
        assertNotNull(response);
        assertEquals(false,response.isSuccess());
        assertEquals(-3,response.getNumDevices());
    }

    @Test
    @DisplayName("Try to get the num devices of a non existing park")
    public void getNumDevicesDNE() throws InvalidRequestException {
        UUID id= UUID.randomUUID();
        FindByParkIdResponse response2= new FindByParkIdResponse(true,null);
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(response2);

        GetNumDevicesRequest request= new GetNumDevicesRequest(id);
        GetNumDevicesResponse response= devicesServices.getNumDevices(request);
        assertNotNull(response);
        assertEquals(false,response.isSuccess());
        assertEquals(-2,response.getNumDevices());
    }

    @Test
    @DisplayName("Get the num devices of an existing park")
    public void getNumDevices() throws InvalidRequestException {
        Park p=new Park();
        UUID id= UUID.randomUUID();
        FindByParkIdResponse response= new FindByParkIdResponse(true,p);
        Mockito.when(parkService.findByParkId(Mockito.any())).thenReturn(response);
        Device w1= new Device();
        List<Device> list= new ArrayList<>();
        list.add(w1);
        list.add(w1);
        Mockito.when(deviceRepo.getAllParkDevices(Mockito.any())).thenReturn(list);

        GetNumDevicesRequest request= new GetNumDevicesRequest(id);
        GetNumDevicesResponse response1= devicesServices.getNumDevices(request);
        assertNotNull(response1);
        assertEquals(true,response1.isSuccess());
        assertEquals(2,response1.getNumDevices());
    }


}
