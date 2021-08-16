package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/park/getParkWaterSites

    //get: /api/park/getAllParksAndSites

    //get: /api/park/getAllParks

    //post: /api/park/editPark

    //post: /api/park/addPark

    //delete: /api/park/deletePark

}
