package za.ac.up.cs.dynative.EParkSmartWaterMonitor.unit;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.DeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.InfrastructureRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
class UnitTestBaseClass {
    final static Logger LOGGER = Logger.getLogger("UnitTestLogger");

    @Autowired
    private ParkRepo parkRepo;

    @Autowired
    private DeviceRepo deviceRepo;

    @Autowired
    InfrastructureRepo infrastructureRepo;

    @Autowired
    MeasurementRepo measurementRepo;

    @Autowired
    WaterSiteRepo waterSiteRepo;

    DevicesService devicesService;
    ParkService parkService;
    WaterSiteService waterSiteService;

    @PostConstruct
    void initialize(){
        LOGGER.info("Testing Constructing ParkService");
        parkService = new ParkServiceImpl(parkRepo);
    }
}
