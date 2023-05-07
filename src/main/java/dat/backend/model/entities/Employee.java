package dat.backend.model.entities;

import java.util.Objects;

public class Employee extends Person {

    private String workPhoneNumber;
    private Position position;
    private Department department;

    public Employee(int id, String email, String name, String password, String workPhoneNumber, String personalPhone, Position position, Department department) {
        super(id, email, name, password, personalPhone);
        this.workPhoneNumber = workPhoneNumber;
        this.position = position;
        this.department = department;
    }

    public String getWorkPhoneNumber() {
        return this.workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
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
        Employee employee = (Employee) other;
        return this.getId() == employee.getId() &&
                this.getEmail().equals(employee.getEmail()) &&
                this.getName().equals(employee.getName()) &&
                this.getPassword().equals(employee.getPassword()) &&
                this.getWorkPhoneNumber().equals(employee.getWorkPhoneNumber()) &&
                this.getPersonalPhoneNumber().equals(employee.getPersonalPhoneNumber()) &&
                this.getPosition().equals(employee.getPosition()) &&
                this.getDepartment().equals(employee.getDepartment());
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