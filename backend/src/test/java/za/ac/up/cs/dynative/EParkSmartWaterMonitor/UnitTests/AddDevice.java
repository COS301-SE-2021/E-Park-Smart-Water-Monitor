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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.AddDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.CanAttachWaterSourceDeviceResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AddDevice {
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
    @DisplayName("Try to add a device, but it already exists")
    public void addDeviceDup(){
        Device device= new Device();
        List<Device> devices=new ArrayList<>();
        devices.add(device);
        Mockito.when(deviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(devices);

        AddDeviceRequest request= new AddDeviceRequest("Unit",UUID.randomUUID(),"XX","test","WaterSource",23,28);
        Throwable t =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request));
        assertEquals("Device already exists",t.getMessage());
    }

    @Test
    @DisplayName("Try and add a device but the request is not complete")
    public void addDeviceIncomplete(){
        AddDeviceRequest request1= new AddDeviceRequest("",UUID.randomUUID(),"XX","test","WaterSource",23,28);
        Throwable t1 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request1));
        assertEquals("Request not complete",t1.getMessage());

        AddDeviceRequest request2= new AddDeviceRequest("Unit",null,"XX","test","WaterSource",23,28);
        Throwable t2 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request2));
        assertEquals("Request not complete",t2.getMessage());

        AddDeviceRequest request3= new AddDeviceRequest("Unit",UUID.randomUUID(),"","test","WaterSource",23,28);
        Throwable t3 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request3));
        assertEquals("Request not complete",t3.getMessage());

        AddDeviceRequest request4= new AddDeviceRequest("Unit",UUID.randomUUID(),"XX","","WaterSource",23,28);
        Throwable t4 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request4));
        assertEquals("Request not complete",t4.getMessage());

        AddDeviceRequest request5= new AddDeviceRequest("",UUID.randomUUID(),"","test","WaterSource",23,28);
        Throwable t5 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request5));
        assertEquals("Request not complete",t5.getMessage());
    }

    @Test
    @DisplayName("Try and add a device but the site does not exists")
    public void addDeviceSiteDNE() throws InvalidRequestException {
        Device device= new Device();
        List<Device> devices=new ArrayList<>();
        Mockito.when(deviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(devices);
        Mockito.when(waterSiteServices.canAttachWaterSourceDevice(Mockito.any())).thenReturn(new CanAttachWaterSourceDeviceResponse("",false));

        AddDeviceRequest request= new AddDeviceRequest("ParkA",UUID.randomUUID(),"XX","test","WaterSource",23,28);
        Throwable t =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request));
        assertEquals("The site does not exist",t.getMessage());
    }


    @Test
    @DisplayName("Successfully add a device to a site")
    public void addDevice() throws InvalidRequestException {
        Device device= new Device();
        List<Device> devices=new ArrayList<>();
        Mockito.when(deviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(devices);
        Mockito.when(waterSiteServices.canAttachWaterSourceDevice(Mockito.any())).thenReturn(new CanAttachWaterSourceDeviceResponse("",true));

        AddDeviceRequest request= new AddDeviceRequest("ParkA",UUID.randomUUID(),"XX","test","WaterSource",23,28);
        AddDeviceResponse response = devicesServices.addDevice(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Device test successfully added",response.getStatus());
    }
}
