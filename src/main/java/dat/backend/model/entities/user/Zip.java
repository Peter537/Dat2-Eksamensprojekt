package dat.backend.model.entities.user;

import java.util.Objects;

public class Zip {

    private int zipCode;
    private String cityName;

    public Zip(int zipCode, String cityName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
    }

    public int getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Zip)) return false;
        Zip zip = (Zip) other;
        return this.getZipCode() == zip.getZipCode() &&
                this.getCityName().equals(zip.getCityName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getZipCode(), this.getCityName());
    }

    @Override
    public String toString() {
        return "Zip{" +
                "zipCode=" + this.zipCode +
                ", cityName='" + this.cityName + '\'' +
                '}';
    }
}