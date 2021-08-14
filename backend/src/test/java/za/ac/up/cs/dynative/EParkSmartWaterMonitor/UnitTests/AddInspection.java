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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.FindDeviceRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.FindDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.InspectionServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.AddInspectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.AddInspectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;

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
        AddInspectionResponse response = inspectionService.addInspection(new AddInspectionRequest(UUID.randomUUID(),null,"abc"));
        Mockito.when(devicesService.findDevice(Mockito.any())).thenReturn(null);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to add inspection! Failure to get device!",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to add an inspection, but the device does not exist part 2")
    public void AddInspectionDeviceNotFound2() throws InvalidRequestException {
        AddInspectionResponse response = inspectionService.addInspection(new AddInspectionRequest(UUID.randomUUID(),null,"abc"));
        Mockito.when(devicesService.findDevice(Mockito.any())).thenReturn(new FindDeviceResponse());
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to add inspection! Failure to get device!",response.getStatus());
    }


//    FindDeviceResponse findDeviceResponse = devicesService.findDevice(new FindDeviceRequest(request.getDeviceId()));
//        if (findDeviceResponse == null || !findDeviceResponse.getSuccess()) {
//        response.setStatus("Failed to add inspection! Failure to get device!");
//        response.setSuccess(false);
//        return response;
//    }
}



