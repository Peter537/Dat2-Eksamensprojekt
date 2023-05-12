package dat.backend.model.entities.user;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class Zip {

    private int zipCode;
    private String cityName;

    public Zip(int zipCode, String cityName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getZipCode() {
        return this.zipCode;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getCityName() {
        return this.cityName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
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

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getZipCode(), this.getCityName());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Zip{" +
                "zipCode=" + this.zipCode +
                ", cityName='" + this.cityName + '\'' +
                '}';
    }
}