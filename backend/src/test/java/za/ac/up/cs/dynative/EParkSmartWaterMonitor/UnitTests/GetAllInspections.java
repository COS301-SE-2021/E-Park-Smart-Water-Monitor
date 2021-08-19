package UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.InspectionServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.models.Inspection;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.repositories.InspectionRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses.GetAllInspectionsResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetAllInspections {

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
    @DisplayName("Try to get all the inspections but there is none")
    public void GetAllInspectionsDNE(){
        //setup
        Mockito.when(parkRepo.findAll()).thenReturn(new ArrayList<>());

        //test
        GetAllInspectionsResponse response = inspectionService.getAllInspections();
        assertNotNull(response);
        assertEquals(new ArrayList<>(),response.getInspections());
    }

    @Test
    @DisplayName("Successfully get all the inspections of a park")
    public void GetAllInspectionsSuccess(){
        //setup
        List<Park> parks = new ArrayList<>();
        Park p1 = new Park();
        parks.add(p1);
        List<List<Inspection>> list=new ArrayList<>();
        List<Inspection> inspections = new ArrayList<>();
        list.add(inspections);
        Mockito.when(parkRepo.findAll()).thenReturn(parks);

        //test
        GetAllInspectionsResponse response = inspectionService.getAllInspections();
        assertNotNull(response);
        assertEquals(list,response.getInspections());
    }
}


