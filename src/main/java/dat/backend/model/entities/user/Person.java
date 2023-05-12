package dat.backend.model.entities.user;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class Person {

    private int id;
    private String email;
    private String name;
    private String password;
    private Optional<String> personalPhoneNumber;

    public Person(int id, String email, String name, String password, Optional<String> personalPhoneNumber) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.personalPhoneNumber = personalPhoneNumber;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getId() {
        return this.id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setId(int id) {
        this.id = id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getEmail() {
        return this.email;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setEmail(String email) {
        this.email = email;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getName() {
        return this.name;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setName(String name) {
        this.name = name;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getPassword() {
        return this.password;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPassword(String password) {
        this.password = password;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<String> getPersonalPhoneNumber() {
        return this.personalPhoneNumber;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPersonalPhoneNumber(Optional<String> personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Person)) return false;
        Person person = (Person) other;
        return this.getId() == person.getId() &&
                this.getEmail().equals(person.getEmail());
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getEmail(), this.getName(), this.getPassword(), this.getPersonalPhoneNumber());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Person{" +
                "id=" + this.id +
                ", email='" + this.email + '\'' +
                ", name='" + this.name + '\'' +
                ", password='" + this.password + '\'' +
                ", phoneNumber='" + this.personalPhoneNumber + '\'' +
                '}';
    }
}