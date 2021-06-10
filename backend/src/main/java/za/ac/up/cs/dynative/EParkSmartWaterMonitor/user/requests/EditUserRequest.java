package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests;

import com.fasterxml.jackson.annotation.JsonProperty;


public class EditUserRequest
{
    private String idNumber;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String username;
    private String role;
    private String cellNumber;

    public EditUserRequest(
                            @JsonProperty("idNumber")String idNumber,
                            @JsonProperty("email")String email,
                            @JsonProperty("password")String password,
                            @JsonProperty("name")String name,
                            @JsonProperty("surname")String surname,
                            @JsonProperty("username")String username,
                            @JsonProperty("role")String role,
                            @JsonProperty("cellNumber")String cellNumber
                          )
    {
        this.idNumber = idNumber;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.role = role;
        this.cellNumber = cellNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

}
