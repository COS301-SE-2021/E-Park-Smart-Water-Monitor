package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.EParkSmartWaterMonitorApplication;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.CreateUserResponse;

import java.util.UUID;

@SpringBootTest(classes = EParkSmartWaterMonitorApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddUser {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void addUser(){
        CreateUserRequest request = new CreateUserRequest(UUID.fromString("28d46124-7581-477d-8231-acffbfe0d896"), "1234567890","test@int.com",
                "intTest","ChiChi","301","chitea","RANGER","0000000000");
        ResponseEntity<CreateUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void addUserDuplicate(){
        CreateUserRequest request = new CreateUserRequest(UUID.fromString("28d46124-7581-477d-8231-acffbfe0d896"), "1234567890","test@int.com",
                "intTest","ChiChi","301","chitea","RANGER","0000000000");
        ResponseEntity<CreateUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void addUserParkNull(){
        CreateUserRequest request = new CreateUserRequest(null, "1234567890","test@int.com",
                "intTest","ChiChi","301","chitea","RANGER","0000000000");
        ResponseEntity<CreateUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(4)
    public void addUserParkDNE(){
        CreateUserRequest request = new CreateUserRequest(UUID.fromString("46d46124-7581-477d-8231-acffbfe0d896"), "1234567890","test@int.com",
                "intTest","ChiChi","301","chitea","RANGER","0000000000");
        ResponseEntity<CreateUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/createUser", request, CreateUserResponse.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }
}

