package dat.backend.model.entities;

import java.util.Objects;

public class City {

    private int id;
    private String cityName;
    private int zipCode;

    public City(int id, String cityName, int zipCode) {
        this.id = id;
        this.cityName = cityName;
        this.zipCode = zipCode;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof City)) return false;
        City city = (City) other;
        return this.getId() == city.getId() &&
                this.getCityName().equals(city.getCityName()) &&
                this.getZipCode() == city.getZipCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getCityName(), this.getZipCode());
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + this.id +
                ", cityName='" + this.cityName + '\'' +
                ", zipCode=" + this.zipCode +
                '}';
    }
}