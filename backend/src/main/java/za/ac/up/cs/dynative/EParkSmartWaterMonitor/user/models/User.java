package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

import java.time.LocalDateTime;
import java.util.UUID;

@Node
public class User {

    @Id
    private UUID id;

    private long idNumber;

    private String email;

    private String name;

    private String surname;

    private String password;

    private String username;

    private String role;

    private String cellNumber;

    private String parkName;

    private String activationCode;

    private LocalDateTime resetPasswordExpiration;

    @Relationship(type = "WORKS_FOR", direction = Relationship.Direction.OUTGOING)
    private Park park;

    public User(long idNumber, String email, String name, String surname, String password, String username, String role, Park park, String cellNumber) {
        this.role = role;
        this.id = UUID.randomUUID();
        this.idNumber = idNumber;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.park = park;
        this.role = role;
        this.cellNumber = cellNumber;
        this.parkName = park.getParkName();
    }

    public User(int idNumber, String email, String name, String surname, String password, String username, String role) {
        this.role = role;
        this.id = UUID.randomUUID();
        this.idNumber = idNumber;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", idNumber='" + idNumber + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", resetCode='" + activationCode + '\'' +
                ", passwordExpiration='" + resetPasswordExpiration + '\'' +
                ", role='" + role + '\'' +
                ", cellNumber='" + cellNumber + '\'' +
                ", park=" + park +
                '}';
    }

    private enum Role {
        SUPERADMIN("ADMIN"),
        FIELDENGINEER("FIELD_ENGINEER"),
        RANGER("RANGER");

        private final String name;

        Role(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
    public String getActivationCode(){
        return activationCode;
    }

    public void setResetPasswordExpiration(LocalDateTime expiration){
        resetPasswordExpiration= expiration;
    }
    public LocalDateTime getResetPasswordExpiration(){
        return resetPasswordExpiration;
    }
}

