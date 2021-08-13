package za.ac.up.cs.dynative.EParkSmartWaterMonitor.UnitTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.SetMetricFrequencyRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.SetMetricFrequencyResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SetMetricFrequency {

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
    @DisplayName("Attempt to set the frequency but the device id is null")
    public void SetMetricFrequencyIdNull() {
        SetMetricFrequencyResponse response = devicesServices.setMetricFrequency(new SetMetricFrequencyRequest(null,0));
        assertNotNull(response);
        assertEquals(false,response.getSuccess());
        assertEquals("No device id specified.",response.getStatus());
    }
}
