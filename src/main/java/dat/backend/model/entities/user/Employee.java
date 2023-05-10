package dat.backend.model.entities.user;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Employee extends Person {

    private Optional<String> workPhoneNumber;
    private Position position;
    private Department department;

    public Employee(int id, String email, String name, String password, Optional<String> personalPhone, Optional<String> workPhoneNumber, Position position, Department department) {
        super(id, email, name, password, personalPhone);
        this.workPhoneNumber = workPhoneNumber;
        this.position = position;
        this.department = department;
    }

    public Optional<String> getWorkPhoneNumber() {
        return this.workPhoneNumber;
    }

    public void setWorkPhoneNumber(Optional<String> workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Employee)) return false;
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getEmail(), this.getName(), this.getPassword(), this.getWorkPhoneNumber(), this.getPersonalPhoneNumber(), this.getPosition(), this.getDepartment());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + this.getId() +
                ", email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", workPhoneNumber='" + this.workPhoneNumber + '\'' +
                ", personalPhoneNumber='" + this.getPersonalPhoneNumber() + '\'' +
                ", position=" + this.position +
                ", department=" + this.department +
                '}';
    }
}