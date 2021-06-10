package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.UUID;

public class LoginResponse {

    private Boolean success;
    private String jwt;
    private String userRole;
    private String userEmail;
    private String name;
    private String surname;
    private String username;
    private String cellNumber;
    private long userIdNumber;
    private UUID parkId;
    private String parkName;

    public LoginResponse(){}

    public LoginResponse(Boolean success,
                         String jwt,
                         String userRole,
                         String userEmail,
                         String name,
                         String surname,
                         String username,
                         String cellNumber,
                         long userIdNumber,
                         UUID parkId,
                         String parkName)
    {
        this.success = success;
        this.jwt = jwt;
        this.userRole = userRole;
        this.userEmail = userEmail;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.cellNumber = cellNumber;
        this.userIdNumber = userIdNumber;
        this.parkId = parkId;
        this.parkName = parkName;
    }

    public LoginResponse(String jwt, Boolean success){
        this.jwt = jwt;
        this.success = success;

    }

    public String getJwt(){
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getParkId() {
        return parkId;
    }

    public void setParkId(UUID parkId) {
        this.parkId = parkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public long getUserIdNumber() {
        return userIdNumber;
    }

    public void setUserIdNumber(long userIdNumber) {
        this.userIdNumber = userIdNumber;
    }
}
