package za.ac.up.cs.dynative.EParkSmartWaterMonitor.unit;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.WaterSourceDeviceRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.repositories.MeasurementRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkServiceImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.repositories.ParkRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.WaterSiteServicesImpl;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.repositories.WaterSiteRepo;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
//@SpringBootTest
class UnitTestBaseClass {
    final static Logger LOGGER = Logger.getLogger("UnitTestLogger");

    @Autowired
    private ParkRepo parkRepo;

    @Autowired
    private WaterSourceDeviceRepo waterSourceDeviceRepo;

    @Autowired
    MeasurementRepo measurementRepo;

    @Autowired
    WaterSiteRepo waterSiteRepo;


    DevicesServicesImpl devicesService;
    ParkServiceImpl parkService;
    WaterSiteServicesImpl waterSiteService;

    @PostConstruct
    void initialize(){
        LOGGER.info("Testing Constructing ParkService");
        parkService = new ParkServiceImpl(parkRepo);
        assert (parkService != null);
        LOGGER.info("DONE Testing Constructing ParkService");
        LOGGER.info("Testing Constructing waterSiteService");
        waterSiteService = new WaterSiteServicesImpl(parkService, waterSiteRepo);;
        assert (waterSiteService != null);
        LOGGER.info("DONE Testing Constructing waterSiteService");;
        LOGGER.info("Testing Constructing devicesService");
        devicesService = new DevicesServicesImpl(waterSourceDeviceRepo, parkService, waterSiteService, measurementRepo);
        assert (devicesService != null);
        LOGGER.info("DONE Testing Constructing devicesService");
    }
}
