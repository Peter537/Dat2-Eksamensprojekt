package dat.backend.model.entities.user;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Customer extends Person {

    private Optional<Address> address1;
    private Optional<Address> address2;
    private Optional<Address> address3;

    public Customer(int id, String email, String name, Optional<String> personalPhoneNumber, Optional<Address> address1, Optional<Address> address2, Optional<Address> address3) {
        super(id, email, name, personalPhoneNumber);
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    /**
     * This method will return an Address object if the address number is between 1 and 3
     *
     * @param number The address number
     * @return An Address object if the address number is between 1 and 3
     * @throws IllegalArgumentException if the address number is not between 1 and 3
     */
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

    /**
     * This method will set an address to the address number if the address number is between 1 and 3
     *
     * @param addressNumber The address number
     * @param address       The address to set
     * @throws IllegalArgumentException if the address number is not between 1 and 3
     */
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

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<Address> getAddress1() {
        return this.address1;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setAddress1(Optional<Address> address1) {
        this.address1 = address1;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<Address> getAddress2() {
        return this.address2;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setAddress2(Optional<Address> address2) {
        this.address2 = address2;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<Address> getAddress3() {
        return this.address3;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setAddress3(Optional<Address> address3) {
        this.address3 = address3;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Customer)) return false;
        return super.equals(other);
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getAddress1(), this.getAddress2(), this.getAddress3());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.getId() +
                ", email='" + this.getEmail() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", personalPhoneNumber='" + this.getPersonalPhoneNumber() + '\'' +
                ", address1='" + this.getAddress1() + '\'' +
                ", address2='" + this.getAddress2() + '\'' +
                ", address3='" + this.getAddress3() + '\'' +
                '}';
    }
}