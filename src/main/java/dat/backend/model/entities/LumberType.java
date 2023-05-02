package dat.backend.model.entities;

import java.util.Objects;

public class LumberType {

    private int id;
    private int width;
    private int height;
    private String name;
    private float meterPrice;

    public LumberType(int id, int width, int height, String name, float meterPrice) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.name = name;
        this.meterPrice = meterPrice;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMeterPrice() {
        return this.meterPrice;
    }

    public void setMeterPrice(float meterPrice) {
        this.meterPrice = meterPrice;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof LumberType)) return false;
        LumberType lumberType = (LumberType) other;
        return this.getId() == lumberType.getId() &&
                this.getWidth() == lumberType.getWidth() &&
                this.getHeight() == lumberType.getHeight() &&
                this.getName().equals(lumberType.getName()) &&
                this.getMeterPrice() == lumberType.getMeterPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getWidth(), this.getHeight(), this.getName(), this.getMeterPrice());
    }

    @Override
    public String toString() {
        return "LumberType{" +
                "id=" + this.id +
                ", width=" + this.width +
                ", height=" + this.height +
                ", name='" + this.name + '\'' +
                ", meterPrice=" + this.meterPrice +
                '}';
    }
}