package dat.backend.model.entities.user;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class Person {

    private final int id;
    private final String email;
    private String name;
    private Optional<String> personalPhoneNumber;
    private byte[] profilePicture;

    public Person(int id, String email, String name, Optional<String> personalPhoneNumber) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.personalPhoneNumber = personalPhoneNumber;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getId() {
        return this.id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getEmail() {
        return this.email;
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
    public Optional<String> getPersonalPhoneNumber() {
        return this.personalPhoneNumber;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPersonalPhoneNumber(Optional<String> personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Person)) return false;
        Person person = (Person) other;
        return this.getId() == person.getId() &&
                this.getEmail().equals(person.getEmail());
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getProfilePicture() {
        if (profilePicture == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(profilePicture);
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public byte[] getProfilePictureRaw() {
        return profilePicture;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getEmail(), this.getName(), this.getPersonalPhoneNumber());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Person{" +
                "id=" + this.id +
                ", email='" + this.email + '\'' +
                ", name='" + this.name + '\'' +
                ", phoneNumber='" + this.personalPhoneNumber + '\'' +
                '}';
    }
}