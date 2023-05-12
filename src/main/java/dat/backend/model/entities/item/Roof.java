package dat.backend.model.entities.item;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class Roof extends Item {

    private float squareMeterPrice;
    private String type;

    public Roof(int id, float squareMeterPrice, String type) {
        super(id);
        this.squareMeterPrice = squareMeterPrice;
        this.type = type;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getSquareMeterPrice() {
        return this.squareMeterPrice;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setSquareMeterPrice(float squareMeterPrice) {
        this.squareMeterPrice = squareMeterPrice;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getType() {
        return this.type;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Roof)) return false;
        Roof roof = (Roof) other;
        return super.equals(other) &&
                this.getSquareMeterPrice() == roof.getSquareMeterPrice() &&
                this.getType().equals(roof.getType());
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getSquareMeterPrice(), this.getType());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Roof{" +
                "id=" + this.getId() +
                ", meterPrice=" + this.squareMeterPrice +
                ", type='" + this.type + '\'' +
                '}';
    }
}