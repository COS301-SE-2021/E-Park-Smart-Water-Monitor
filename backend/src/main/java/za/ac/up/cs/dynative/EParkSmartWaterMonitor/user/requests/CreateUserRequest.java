package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateUserRequest {

    private String idNumber;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String username;
    private String role;
    private UUID parkId;

    public CreateUserRequest( @JsonProperty("parkId")UUID parkId,
                              @JsonProperty("idNumber")String idNumber,
                              @JsonProperty("email")String email,
                              @JsonProperty("password")String password,
                              @JsonProperty("name")String name,
                              @JsonProperty("surname")String surname,
                              @JsonProperty("username")String username,
                              @JsonProperty("role")String role) {
        this.idNumber = idNumber;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.role = role;
        this.parkId = parkId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public UUID getParkId() {
        return parkId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setParkId(UUID parkId) {
        this.parkId = parkId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


