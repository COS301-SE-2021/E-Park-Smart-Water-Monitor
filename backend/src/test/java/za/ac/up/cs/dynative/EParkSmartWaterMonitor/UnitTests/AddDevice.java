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
    @DisplayName("Try to add a device that already exists.")
    public void addDeviceDuplicate() throws InvalidRequestException {
        //setup
        Device device= new Device();
        List<Device> devices=new ArrayList<>();
        devices.add(device);
        Mockito.when(deviceRepo.findDeviceByDeviceName("test")).thenReturn(devices);

        //test
        AddDeviceRequest request= new AddDeviceRequest("Unit",UUID.randomUUID(),"XX","test","WaterSource",23,28);
        AddDeviceResponse response = devicesServices.addDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Device test already exists.",response.getStatus());
    }

    @Test
    @DisplayName("Try and add a device but the request is not complete")
    public void addDeviceIncomplete() throws InvalidRequestException {
        //test 1
        AddDeviceRequest request1= new AddDeviceRequest("",UUID.randomUUID(),"XX","test","WaterSource",23,28);
        AddDeviceResponse response = devicesServices.addDevice(request1);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request is missing parameters.",response.getStatus());

        //test 2
        AddDeviceRequest request2= new AddDeviceRequest("Unit",null,"XX","test","WaterSource",23,28);
        AddDeviceResponse response2 = devicesServices.addDevice(request2);
        assertNotNull(response2);
        assertEquals(false,response2.getSuccess());
        assertEquals("Request is missing parameters.",response2.getStatus());

        //test 3
        AddDeviceRequest request3= new AddDeviceRequest("Unit",UUID.randomUUID(),"","test","WaterSource",23,28);
        AddDeviceResponse response3 = devicesServices.addDevice(request3);
        assertNotNull(response3);
        assertEquals(false,response3.getSuccess());
        assertEquals("Request is missing parameters.",response3.getStatus());

        //test 4
        AddDeviceRequest request4= new AddDeviceRequest("Unit",UUID.randomUUID(),"XX","","WaterSource",23,28);
        AddDeviceResponse response4 = devicesServices.addDevice(request4);
        assertNotNull(response4);
        assertEquals(false,response4.getSuccess());
        assertEquals("Request is missing parameters.",response4.getStatus());

        //test 5
        AddDeviceRequest request5= new AddDeviceRequest("",UUID.randomUUID(),"","test","WaterSource",23,28);
        AddDeviceResponse response5 = devicesServices.addDevice(request5);
        assertNotNull(response5);
        assertEquals(false,response5.getSuccess());
        assertEquals("Request is missing parameters.",response5.getStatus());
    }

    @Test
    @DisplayName("Try and add a device to a non-existing site")
    public void addDeviceSiteDNE() throws InvalidRequestException {
        //setup
        Device device= new Device();
        List<Device> devices=new ArrayList<>();
        //Mockito.when(deviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(devices);
        Mockito.when(waterSiteServices.canAttachWaterSourceDevice(Mockito.any())).thenReturn(new CanAttachWaterSourceDeviceResponse("",false));

        //test
        AddDeviceRequest request= new AddDeviceRequest("ParkA",UUID.randomUUID(),"XX","test","WaterSource",23,28);
        AddDeviceResponse response= devicesServices.addDevice(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("The water site "+request.getSiteId()+ " does not exist.",response.getStatus());
    }


    @Test
    @DisplayName("Successfully add a device to a site")
    public void addDevice() throws InvalidRequestException {
        //setup
        Device device= new Device();
        List<Device> devices=new ArrayList<>();
        //Mockito.when(deviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(devices);
        Mockito.when(waterSiteServices.canAttachWaterSourceDevice(Mockito.any())).thenReturn(new CanAttachWaterSourceDeviceResponse("",true));

        //test
        AddDeviceRequest request= new AddDeviceRequest("ParkA",UUID.randomUUID(),"XX","test","WaterSource",23,28);
        AddDeviceResponse response = devicesServices.addDevice(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Device test successfully added",response.getStatus());
    }
}
