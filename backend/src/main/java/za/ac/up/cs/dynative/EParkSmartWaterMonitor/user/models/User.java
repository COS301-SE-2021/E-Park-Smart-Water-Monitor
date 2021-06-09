package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;

import java.util.UUID;

@Node
public class User {

    @Id
    private UUID id;

    private String idNumber;

    private String email;

    private String name;

    private String surname;

    private String password;

    private String username;

    @Relationship(type = "WORKS_FOR", direction = Relationship.Direction.OUTGOING)
    private Park park;

    public User(String idNumber, String email, String name, String surname, String password, String username, Park park) {
        this.id = UUID.randomUUID();
        this.idNumber = idNumber;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.park = park;
    }

    public User(String idNumber, String email, String name, String surname, String password, String username) {
        this.id = UUID.randomUUID();
        this.idNumber = idNumber;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", park=" + park +
                '}';
    }
}

