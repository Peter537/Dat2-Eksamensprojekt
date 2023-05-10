package dat.backend.model.entities.user;

import java.util.Objects;

public class Department {

    private int id;
    private String departmentName;
    private Address address;

    public Department(int id, String departmentName, Address address) {
        this.id = id;
        this.departmentName = departmentName;
        this.address = address;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Department)) return false;
        Department department = (Department) other;
        return this.getId() == department.getId() &&
                this.getDepartmentName().equals(department.getDepartmentName()) &&
                this.getAddress().equals(department.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getDepartmentName(), this.getAddress());
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + this.id +
                ", departmentName='" + this.departmentName + '\'' +
                ", address=" + this.address +
                '}';
    }
}