package dat.backend.model.entities.user;

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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional<String> getPersonalPhoneNumber() {
        return this.personalPhoneNumber;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getEmail(), this.getName(), this.getPassword(), this.getPersonalPhoneNumber());
    }

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