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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.ReceiveDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ReceiveWaterDeviceData {

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
    @DisplayName("Try to receive device data but the device name does not exist.")
    public void receiveDeviceDataDeviceNameDNE(){
        //setup
        ReceiveDeviceDataRequest request= new ReceiveDeviceDataRequest("abc",null);
        List<Device> devices = new ArrayList<>();
        Mockito.when(deviceRepo.findDeviceByDeviceName("abc")).thenReturn(devices);

        //test
        ReceiveDeviceDataResponse response= devicesServices.receiveWaterDeviceData(request);
        assertNotNull(response);
        assertEquals("Device with that name does not exist",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Try to receive device data but the device is null.")
    public void receiveDeviceDataDeviceNull(){
        //setup
        ReceiveDeviceDataRequest request= new ReceiveDeviceDataRequest("abc",null);
        List<Device> devices = new ArrayList<>();
        Device device = null;
        devices.add(device);
        Mockito.when(deviceRepo.findDeviceByDeviceName("abc")).thenReturn(devices);

        //test
        ReceiveDeviceDataResponse response= devicesServices.receiveWaterDeviceData(request);
        assertNotNull(response);
        assertEquals("Request Failed... fix not applied!",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Try to receive device data but the device name is not specified.")
    public void receiveDeviceDataNoDeviceDataSpecified(){
        //setup
        ReceiveDeviceDataRequest request= new ReceiveDeviceDataRequest("",null);

        //test
        ReceiveDeviceDataResponse response= devicesServices.receiveWaterDeviceData(request);
        assertNotNull(response);
        assertEquals("No device name is specified.",response.getStatus());
        assertEquals(false,response.getSuccess());
    }

    @Test
    @DisplayName("Successfully receive device data.")
    public void receiveDeviceDataSuccess(){
        //setup
        List<Measurement> measurements = new ArrayList<>();
        Measurement m = new Measurement("a",12,"km","ll",12,12);
        measurements.add(m);
        ReceiveDeviceDataRequest request= new ReceiveDeviceDataRequest("abc",measurements);
        List<Device> devices = new ArrayList<>();
        Device device = new Device();
        devices.add(device);
        Mockito.when(deviceRepo.findDeviceByDeviceName("abc")).thenReturn(devices);

        //test
        ReceiveDeviceDataResponse response= devicesServices.receiveWaterDeviceData(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Successfully added data send from ESP: "
                + request.getDeviceName()
                + " sent at: "
                + request.getMeasurements().get(0).getDeviceDateTime(),response.getStatus());
    }
}
