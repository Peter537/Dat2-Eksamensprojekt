package dat.backend.model.entities.item;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class LumberType implements Comparable<LumberType> {

    private final int id;
    private float width;
    private float thickness;
    private float meterPrice;
    private String type;

    public LumberType(int id, float width, float thickness, float meterPrice, String type) {
        this.id = id;
        this.width = width;
        this.thickness = thickness;
        this.meterPrice = meterPrice;
        this.type = type;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getId() {
        return this.id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getWidth() {
        return this.width;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setWidth(float width) {
        this.width = width;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getThickness() {
        return this.thickness;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getMeterPrice() {
        return this.meterPrice;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setMeterPrice(float meterPrice) {
        this.meterPrice = meterPrice;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getType() {
        return this.type;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setType(String type) {
        this.type = type;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof LumberType)) return false;
        LumberType lumberType = (LumberType) other;
        return this.getId() == lumberType.getId() &&
                this.getWidth() == lumberType.getWidth() &&
                this.getThickness() == lumberType.getThickness() &&
                this.getType().equals(lumberType.getType()) &&
                this.getMeterPrice() == lumberType.getMeterPrice();
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getWidth(), this.getThickness(), this.getType(), this.getMeterPrice());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "LumberType{" +
                "id=" + this.id +
                ", width=" + this.width +
                ", height=" + this.thickness +
                ", name='" + this.type + '\'' +
                ", meterPrice=" + this.meterPrice +
                '}';
    }

    @Override
    public int compareTo(LumberType other) {
        return Integer.compare((int) this.width, (int) other.width);
    }
}