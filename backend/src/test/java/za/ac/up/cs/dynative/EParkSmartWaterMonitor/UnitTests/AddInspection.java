package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.InspectionServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.AddInspectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;

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
}



