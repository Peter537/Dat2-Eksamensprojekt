package dat.backend.model.entities.user;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class Address {

    private final String street;
    private final Zip zip;

    public Address(String street, Zip zip) {
        this.street = street;
        this.zip = zip;
    }

    public String getAddress() {
        return this.street + ", " + this.zip.getZipCode() + " " + this.zip.getCityName();
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getStreet() {
        return this.street;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Zip getZip() {
        return this.zip;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Address)) return false;
        Address address = (Address) other;
        return this.getStreet().equals(address.getStreet()) &&
                this.getZip().equals(address.getZip());
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getStreet(), this.getZip());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Address{" +
                "street='" + this.street + '\'' +
                ", zip=" + this.zip +
                '}';
    }
}