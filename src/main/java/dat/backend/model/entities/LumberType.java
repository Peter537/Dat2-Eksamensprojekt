package dat.backend.model.entities;

import java.util.Objects;

public class LumberType implements Comparable<LumberType>{

    private int id;
    private float width;
    private float thickness;
    private float meterPrice;
    private String type;

    public LumberType(int id, float width, float thickness, float meterPrice) {
        this.id = id;
        this.width = width;
        this.thickness = thickness;
        this.meterPrice = meterPrice;
    }
    public LumberType(int id, float width, float thickness, float meterPrice, String type) {
        this.id = id;
        this.width = width;
        this.thickness = thickness;
        this.meterPrice = meterPrice;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getThickness() {
        return this.thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public float getMeterPrice() {
        return this.meterPrice;
    }

    public void setMeterPrice(float meterPrice) {
        this.meterPrice = meterPrice;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getWidth(), this.getThickness(), this.getType(), this.getMeterPrice());
    }

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