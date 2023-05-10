package dat.backend.model.entities.user;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Customer extends Person {

    private Optional<Address> address1;
    private Optional<Address> address2;
    private Optional<Address> address3;

    public Customer(int id, String email, String name, String password, Optional<String> personalPhoneNumber, Optional<Address> address1, Optional<Address> address2, Optional<Address> address3) {
        super(id, email, name, password, personalPhoneNumber);
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public Optional<Address> getAddress(int number) {
        switch (number) {
            case 1:
                return this.address1;
            case 2:
                return this.address2;
            case 3:
                return this.address3;
            default:
                throw new IllegalArgumentException("Address number must be between 1 and 3");
        }
    }

    public void setAddress(int addressNumber, Optional<Address> address) {
        switch (addressNumber) {
            case 1:
                this.address1 = address;
                break;
            case 2:
                this.address2 = address;
                break;
            case 3:
                this.address3 = address;
                break;
            default:
                throw new IllegalArgumentException("Address number must be between 1 and 3");
        }
    }

    public Optional<Address> getAddress1() {
        return this.address1;
    }

    public void setAddress1(Optional<Address> address1) {
        this.address1 = address1;
    }

    public Optional<Address> getAddress2() {
        return this.address2;
    }

    public void setAddress2(Optional<Address> address2) {
        this.address2 = address2;
    }

    public Optional<Address> getAddress3() {
        return this.address3;
    }

    public void setAddress3(Optional<Address> address3) {
        this.address3 = address3;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Customer)) return false;
        return super.equals(other);
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