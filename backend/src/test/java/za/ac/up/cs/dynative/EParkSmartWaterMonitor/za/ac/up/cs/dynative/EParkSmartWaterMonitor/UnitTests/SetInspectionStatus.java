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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.requests.SetInspectionStatusRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.SetInspectionStatusResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SetInspectionStatus {

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
    @DisplayName("Try to set an inspection status but the request is null")
    public void SetInspectionStatusRequestNull(){
        SetInspectionStatusResponse response = inspectionService.setInspectionStatus(null);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Request is null",response.getStatus());
    }

    @Test
    @DisplayName("Try to set an inspection status but the inspection id is null")
    public void SetInspectionStatusIdNull(){
        SetInspectionStatusRequest request = new SetInspectionStatusRequest(null,"Done");
        SetInspectionStatusResponse response = inspectionService.setInspectionStatus(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to set inspection status! Invalid inspectionId!",response.getStatus());
    }

    @Test
    @DisplayName("Try to set an inspection status but the inspection does not exist")
    public void SetInspectionStatusInspectionDNE(){
        //setup
        SetInspectionStatusRequest request = new SetInspectionStatusRequest(UUID.randomUUID(),"Done");
        Mockito.when(inspectionRepo.findInspectionById(Mockito.any())).thenReturn(null);

        //test
        SetInspectionStatusResponse response = inspectionService.setInspectionStatus(request);
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("Failed to set inspection status! Inspection not found!",response.getStatus());
    }

    @Test
    @DisplayName("Try to set an inspection and succeed")
    public void SetInspectionStatusSuccess(){
        //setup
        SetInspectionStatusRequest request = new SetInspectionStatusRequest(UUID.randomUUID(),"Done");
        Mockito.when(inspectionRepo.findInspectionById(Mockito.any())).thenReturn(new Inspection(new Device(),UUID.randomUUID(),UUID.randomUUID(), new Date(),"abc"));

        //test
        SetInspectionStatusResponse response = inspectionService.setInspectionStatus(request);
        assertNotNull(response);
        assertEquals(true,response.getSuccess());
        assertEquals("Inspection status successfully set!",response.getStatus());
    }
}

