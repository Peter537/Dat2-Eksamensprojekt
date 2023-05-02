package dat.backend.model.entities;

import java.util.Objects;

public class Address {

    private int id;
    private String street;
    private City city;

    public Address(int id, String street, City city) {
        this.id = id;
        this.street = street;
        this.city = city;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Address)) return false;
        Address address = (Address) other;
        return this.getId() == address.getId() &&
                this.getStreet().equals(address.getStreet()) &&
                this.getCity().equals(address.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getStreet(), this.getCity());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + this.id +
                ", street='" + this.street + '\'' +
                ", city=" + this.city +
                '}';
    }
}