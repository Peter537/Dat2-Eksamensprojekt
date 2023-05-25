package dat.backend.model.entities.item;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class Roof {

    private final int id;
    private final String type;
    private final String displayName;

    private float squareMeterPrice;

    public Roof(int id, float squareMeterPrice, String type, String displayName) {
        this.id = id;
        this.squareMeterPrice = squareMeterPrice;
        this.type = type;
        this.displayName = displayName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getId() {
        return this.id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getType() {
        return this.type;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getDisplayName() {
        return this.displayName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getSquareMeterPrice() {
        return this.squareMeterPrice;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setSquareMeterPrice(float squareMeterPrice) {
        this.squareMeterPrice = squareMeterPrice;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Roof)) return false;
        Roof roof = (Roof) other;
        return this.getId() == roof.getId() &&
                this.getType().equals(roof.getType());
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getSquareMeterPrice(), this.getType());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Roof{" +
                "id=" + this.id +
                ", meterPrice=" + this.squareMeterPrice +
                ", type='" + this.type + '\'' +
                '}';
    }
}