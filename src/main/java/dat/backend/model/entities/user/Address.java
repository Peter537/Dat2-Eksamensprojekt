package dat.backend.model.entities.user;

import java.util.Objects;

public class Address {

    private String street;
    private Zip zip;

    public Address(String street, Zip zip) {
        this.street = street;
        this.zip = zip;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Zip getZip() {
        return this.zip;
    }

    public void setZip(Zip zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Address)) return false;
        Address address = (Address) other;
        return this.getStreet().equals(address.getStreet()) &&
                this.getZip().equals(address.getZip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getStreet(), this.getZip());
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + this.street + '\'' +
                ", zip=" + this.zip +
                '}';
    }
}