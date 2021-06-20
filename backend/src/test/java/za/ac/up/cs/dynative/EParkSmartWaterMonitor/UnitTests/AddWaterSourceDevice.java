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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.AddWaterSourceDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.CanAttachWaterSourceDeviceResponse;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AddWaterSourceDevice {
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
    @DisplayName("Try to add a device, but it already exists")
    public void addDeviceDup(){
        WaterSourceDevice device= new WaterSourceDevice();
        List<WaterSourceDevice> devices=new ArrayList<>();
        devices.add(device);
        Mockito.when(waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(devices);

        AddWaterSourceDeviceRequest request= new AddWaterSourceDeviceRequest("Unit",UUID.randomUUID(),"XX","test",23,28);
        Throwable t =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request));
        assertEquals("Device already exists",t.getMessage());
    }

    @Test
    @DisplayName("Try and add a device but the request is not complete")
    public void addDeviceIncomplete(){
        AddWaterSourceDeviceRequest request1= new AddWaterSourceDeviceRequest("",UUID.randomUUID(),"XX","test",23,28);
        Throwable t1 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request1));
        assertEquals("Request not complete",t1.getMessage());

        AddWaterSourceDeviceRequest request2= new AddWaterSourceDeviceRequest("Unit",null,"XX","test",23,28);
        Throwable t2 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request2));
        assertEquals("Request not complete",t2.getMessage());

        AddWaterSourceDeviceRequest request3= new AddWaterSourceDeviceRequest("Unit",UUID.randomUUID(),"","test",23,28);
        Throwable t3 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request3));
        assertEquals("Request not complete",t3.getMessage());

        AddWaterSourceDeviceRequest request4= new AddWaterSourceDeviceRequest("Unit",UUID.randomUUID(),"XX","",23,28);
        Throwable t4 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request4));
        assertEquals("Request not complete",t4.getMessage());

        AddWaterSourceDeviceRequest request5= new AddWaterSourceDeviceRequest("",UUID.randomUUID(),"","test",23,28);
        Throwable t5 =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request5));
        assertEquals("Request not complete",t5.getMessage());
    }

    @Test
    @DisplayName("Try and add a device but the site does not exists")
    public void addDeviceSiteDNE() throws InvalidRequestException {
        WaterSourceDevice device= new WaterSourceDevice();
        List<WaterSourceDevice> devices=new ArrayList<>();
        Mockito.when(waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(devices);
        Mockito.when(waterSiteServices.canAttachWaterSourceDevice(Mockito.any())).thenReturn(new CanAttachWaterSourceDeviceResponse("",false));

        AddWaterSourceDeviceRequest request= new AddWaterSourceDeviceRequest("ParkA",UUID.randomUUID(),"XX","test",23,28);
        Throwable t =assertThrows(InvalidRequestException.class, ()->devicesServices.addDevice(request));
        assertEquals("The site does not exist",t.getMessage());
    }

    //TODO: fix the application.properties the come back and uncomment
//    @Test
//    @DisplayName("Successfully add a device to a site")
//    public void addDevice() throws InvalidRequestException {
//        WaterSourceDevice device= new WaterSourceDevice();
//        List<WaterSourceDevice> devices=new ArrayList<>();
//        Mockito.when(waterSourceDeviceRepo.findWaterSourceDeviceByDeviceName("test")).thenReturn(devices);
//        Mockito.when(waterSiteServices.canAttachWaterSourceDevice(Mockito.any())).thenReturn(new CanAttachWaterSourceDeviceResponse("",true));
//
//        AddWaterSourceDeviceRequest request= new AddWaterSourceDeviceRequest("ParkA",UUID.randomUUID(),"XX","test",23,28);
//        AddWaterSourceDeviceResponse response = devicesServices.addDevice(request);
//        assertNotNull(response);
//        assertEquals(true,response.getSuccess());
//        assertEquals("Device test successfully added",response.getStatus());
//    }
}
