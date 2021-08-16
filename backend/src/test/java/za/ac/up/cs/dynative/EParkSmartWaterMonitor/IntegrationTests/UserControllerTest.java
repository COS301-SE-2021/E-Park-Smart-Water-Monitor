package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    //post: /api/user/createUser

    //post: /api/user/deleteUser

    //post: /api/user/editUser

    //get: /api/user/getAllUsers

    //post: /api/user/getUser

    //post: /api/user/login

    //post: /api/user/resetPassword
    //post: /api/user/resetPasswordFinalize
}
