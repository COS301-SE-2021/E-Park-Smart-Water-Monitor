package UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.InspectionServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.GetDeviceInspectionsRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.GetDeviceInspectionsResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetDeviceInspections {

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
    @DisplayName("Try to set an inspection of device but the request is null")
    public void GetDeviceInspectionRequestNull(){
        GetDeviceInspectionsResponse response = inspectionService.getDeviceInspections(null);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request is null",response.getStatus());
    }

    @Test
    @DisplayName("Try to set an inspection status but the inspection id is null")
    public void GetDeviceInspectionIdNull(){
        GetDeviceInspectionsRequest request = new GetDeviceInspectionsRequest(null);
        GetDeviceInspectionsResponse response = inspectionService.getDeviceInspections(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to get inspection! Invalid deviceId!",response.getStatus());
    }

    @Test
    @DisplayName("Try to get device inspections and succeed")
    public void GetDeviceInspectionsSuccess(){
        GetDeviceInspectionsRequest request = new GetDeviceInspectionsRequest(UUID.randomUUID());
        GetDeviceInspectionsResponse response = inspectionService.getDeviceInspections(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Inspections retrieved successfully!",response.getStatus());
    }
}

