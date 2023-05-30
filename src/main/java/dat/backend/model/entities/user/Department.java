package dat.backend.model.entities.user;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class Department {

    private final int id;
    private final String departmentName;
    private final Address address;

    public Department(int id, String departmentName, Address address) {
        this.id = id;
        this.departmentName = departmentName;
        this.address = address;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getId() {
        return this.id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getDepartmentName() {
        return this.departmentName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Address getAddress() {
        return this.address;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Department)) return false;
        Department department = (Department) other;
        return this.getId() == department.getId();
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getDepartmentName(), this.getAddress());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Department{" +
                "id=" + this.id +
                ", departmentName='" + this.departmentName + '\'' +
                ", address=" + this.address +
                '}';
    }
}