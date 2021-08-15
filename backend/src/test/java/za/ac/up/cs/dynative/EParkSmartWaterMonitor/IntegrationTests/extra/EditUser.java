package za.ac.up.cs.dynative.EParkSmartWaterMonitor.IntegrationTests.extra;
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
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.EditUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.EditUserResponse;


@SpringBootTest(classes = EParkSmartWaterMonitorApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EditUser {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void EditUserNoChanges(){


        EditUserRequest request = new EditUserRequest("GONERSOON","9971219577231","gone@gmail.com",
                "team","dynative","GONERSOON","Admin","0125643412");
        ResponseEntity<EditUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/editUser", request, EditUserResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(2)
    public void EditUserNewUsername(){
        EditUserRequest request = new EditUserRequest("GONERSOON","9971219577231","gone@gmail.com",
                "team","dynative","g2g","Admin","0125643412");
        ResponseEntity<EditUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/editUser", request, EditUserResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void EditUserChangeName(){
        EditUserRequest request = new EditUserRequest("g2g","9971219577231","gone@gmail.com",
                "Team","dynative","GONERSOON","Admin","0125643412");
        ResponseEntity<EditUserResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/user/editUser", request, EditUserResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

}

