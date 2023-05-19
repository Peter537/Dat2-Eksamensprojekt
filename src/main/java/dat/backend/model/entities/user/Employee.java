package dat.backend.model.entities.user;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Employee extends Person {

    private Optional<String> workPhoneNumber;
    private Position position;
    private Department department;

    public Employee(int id, String email, String name, Optional<String> personalPhone, Optional<String> workPhoneNumber, Position position, Department department) {
        super(id, email, name, personalPhone, true);
        this.workPhoneNumber = workPhoneNumber;
        this.position = position;
        this.department = department;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<String> getWorkPhoneNumber() {
        return this.workPhoneNumber;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setWorkPhoneNumber(Optional<String> workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Position getPosition() {
        return this.position;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPosition(Position position) {
        this.position = position;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Department getDepartment() {
        return this.department;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setDepartment(Department department) {
        this.department = department;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Employee)) return false;
        return super.equals(other);
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getWorkPhoneNumber(), this.getPosition(), this.getDepartment());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + this.getId() +
                ", email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", workPhoneNumber='" + this.workPhoneNumber + '\'' +
                ", personalPhoneNumber='" + this.getPersonalPhoneNumber() + '\'' +
                ", position=" + this.position +
                ", department=" + this.department +
                '}';
    }
}