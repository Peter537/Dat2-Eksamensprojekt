package dat.backend.model.entities;

import java.util.Objects;

public class Customer extends Person {

    private Address address1;
    private Address address2;
    private Address address3;

    public Customer(int id, String email, String name, String password, String personalPhoneNumber, Address address1, Address address2, Address address3) {
        super(id, email, name, password, personalPhoneNumber);
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public Address getAddress1() {
        return this.address1;
    }

    public void setAddress1(Address address1) {
        this.address1 = address1;
    }

    public Address getAddress2() {
        return this.address2;
    }

    public void setAddress2(Address address2) {
        this.address2 = address2;
    }

    public Address getAddress3() {
        return this.address3;
    }

    public void setAddress3(Address address3) {
        this.address3 = address3;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Customer)) return false;
        Customer customer = (Customer) other;
        return this.getId() == customer.getId() &&
                this.getEmail().equals(customer.getEmail()) &&
                this.getName().equals(customer.getName()) &&
                this.getPassword().equals(customer.getPassword()) &&
                this.getPersonalPhoneNumber().equals(customer.getPersonalPhoneNumber()) &&
                this.getAddress1().equals(customer.getAddress1()) &&
                this.getAddress2().equals(customer.getAddress2()) &&
                this.getAddress3().equals(customer.getAddress3());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getEmail(), this.getName(), this.getPassword(), this.getPersonalPhoneNumber(), this.getAddress1(), this.getAddress2(), this.getAddress3());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.getId() +
                ", email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", personalPhoneNumber='" + this.getPersonalPhoneNumber() + '\'' +
                ", address1='" + this.getAddress1() + '\'' +
                ", address2='" + this.getAddress2() + '\'' +
                ", address3='" + this.getAddress3() + '\'' +
                '}';
    }
}