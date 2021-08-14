package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.GetWaterSiteInspectionsRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.GetWaterSiteInspectionsResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetWaterSiteInspection {

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
    @DisplayName("Attempt to get the water site inspection but the request is null")
    public void GetWaterSiteInspectionRequestNull(){
        GetWaterSiteInspectionsResponse response= inspectionService.getWaterSiteInspections(null);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request is null",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to get the water site inspection but the id is null")
    public void GetWaterSiteInspectionIdNull(){
        GetWaterSiteInspectionsResponse response= inspectionService.getWaterSiteInspections(new GetWaterSiteInspectionsRequest(null));
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to get inspection! Invalid waterSiteId!",response.getStatus());
    }

    @Test
    @DisplayName("Attempt to get the water site and succeed")
    public void GetWaterSiteInspectionSuccess(){
        GetWaterSiteInspectionsResponse response= inspectionService.getWaterSiteInspections(new GetWaterSiteInspectionsRequest(UUID.randomUUID()));
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Inspections retrieved successfully!",response.getStatus());
    }
}

