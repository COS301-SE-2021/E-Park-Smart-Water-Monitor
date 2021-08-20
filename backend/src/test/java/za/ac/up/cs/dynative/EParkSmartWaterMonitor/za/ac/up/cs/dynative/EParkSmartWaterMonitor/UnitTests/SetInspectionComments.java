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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.InspectionServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.SetInspectionCommentsRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.SetInspectionCommentsResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SetInspectionComments {

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
    @DisplayName("Try to set an inspection comment but the request is null")
    public void SetInspectionCommentRequestNull(){
        SetInspectionCommentsResponse response = inspectionService.setInspectionComments(null);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request is null",response.getStatus());
    }

    @Test
    @DisplayName("Try to set an inspection comment but the inspection id is null")
    public void SetInspectionCommentIdNull(){
        SetInspectionCommentsRequest request = new SetInspectionCommentsRequest(null, "abc");
        SetInspectionCommentsResponse response = inspectionService.setInspectionComments(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to set inspection comments! Invalid inspectionId!",response.getStatus());
    }

    @Test
    @DisplayName("Try to set an inspection comment but the inspection does not exist")
    public void SetInspectionCommentInspectionDNE(){
        //setup
        SetInspectionCommentsRequest request = new SetInspectionCommentsRequest(UUID.randomUUID(), "abc");
        Mockito.when(inspectionRepo.findInspectionById(Mockito.any())).thenReturn(null);

        //test
        SetInspectionCommentsResponse response = inspectionService.setInspectionComments(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to set inspection comments! Inspection not found!",response.getStatus());
    }

    @Test
    @DisplayName("Try to set an inspection comment and succeed")
    public void SetInspectionCommentSuccessful(){
        //setup
        SetInspectionCommentsRequest request = new SetInspectionCommentsRequest(UUID.randomUUID(), "abc");
        Mockito.when(inspectionRepo.findInspectionById(Mockito.any())).thenReturn(new Inspection(new Device(),UUID.randomUUID(),UUID.randomUUID(), new Date(),"abc"));

        //test
        SetInspectionCommentsResponse response = inspectionService.setInspectionComments(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Inspection comments successfully set!",response.getStatus());
    }
}


