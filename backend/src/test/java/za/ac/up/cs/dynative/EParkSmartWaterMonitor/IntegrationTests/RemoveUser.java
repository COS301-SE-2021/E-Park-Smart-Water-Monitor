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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.DeleteUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.DeleteUserResponse;

import java.util.UUID;

@SpringBootTest(classes = EParkSmartWaterMonitorApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RemoveUser {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void RemoveUser(){
        DeleteUserRequest request = new DeleteUserRequest(UUID.fromString("48356a22-e568-484b-bc5d-4aa35b07e86a"));
        ResponseEntity<DeleteUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/deleteUser", request, DeleteUserResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void removeUserDNE(){
        DeleteUserRequest request = new DeleteUserRequest(UUID.fromString("77356a22-e568-484b-bc5d-4aa35b07e86a"));
        ResponseEntity<DeleteUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/deleteUser", request, DeleteUserResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}

