package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Device;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.InspectionServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.AddInspectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.AddInspectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AddInspection {

    @Mock
    private InspectionRepo inspectionRepo;
    @Mock
    private ParkRepo parkRepo;

    @Mock(name = "DeviceServiceImpl")
    private DevicesService devicesService;
    @Mock(name = "WaterSiteServiceImpl")
    private WaterSiteService waterSiteService;

    @InjectMocks
    private InspectionServiceImpl inspectionService;

    @Test
    @DisplayName("Attempt to add an inspection, but the request is null")
    public void AddInspectionRequestNull() throws InvalidRequestException {
        AddInspectionResponse response = inspectionService.addInspection(null);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request is null",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to add an inspection, but the id is null")
    public void AddInspectionIdNull() throws InvalidRequestException {
        AddInspectionResponse response = inspectionService.addInspection(new AddInspectionRequest(null,null,"abc"));
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to add inspection! No deviceId specified!",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to add an inspection, but the device does not exist")
    public void AddInspectionDeviceNotFound() throws InvalidRequestException {
        //setup
        Mockito.when(devicesService.findDevice(Mockito.any())).thenReturn(null);

        //test
        AddInspectionResponse response = inspectionService.addInspection(new AddInspectionRequest(UUID.randomUUID(),null,"abc"));
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to add inspection! Failure to get device!",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to add an inspection, but the device does not exist part 2")
    public void AddInspectionDeviceNotFound2() throws InvalidRequestException {
        //setup
        FindDeviceResponse re = new FindDeviceResponse();
        re.setSuccess(false);
        Mockito.when(devicesService.findDevice(Mockito.any())).thenReturn(re);

        //test
        AddInspectionResponse response = inspectionService.addInspection(new AddInspectionRequest(UUID.randomUUID(),null,"abc"));
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to add inspection! Failure to get device!",response.getStatus());
    }

    @Test
    @DisplayName("Add inspection successfully")
    public void AddInspectionSuccess() throws InvalidRequestException {
        //setup
        FindDeviceResponse re = new FindDeviceResponse();
        re.setSuccess(true);
        re.setDevice(new Device());
        Mockito.when(waterSiteService.getWaterSiteByRelatedDevice(Mockito.any())).thenReturn(new WaterSite());
        Mockito.when(devicesService.findDevice(Mockito.any())).thenReturn(re);

        //response
        AddInspectionResponse response = inspectionService.addInspection(new AddInspectionRequest(UUID.randomUUID(),null,"abc"));
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Inspection successfully added!",response.getStatus());
    }


}



