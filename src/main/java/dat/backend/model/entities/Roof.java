package dat.backend.model.entities;

import java.util.Objects;

public class Roof extends Item {

    private int id;
    private float SquareMeterPrice;
    private String type;

    public Roof(int id, float SquareMeterPrice, String type) {
        super(id, 0);
        this.id = id;
        this.SquareMeterPrice = SquareMeterPrice;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSquareMeterPrice() {
        return this.SquareMeterPrice;
    }

    public void setSquareMeterPrice(float squareMeterPrice) {
        this.SquareMeterPrice = squareMeterPrice;
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
        if (!(other instanceof Roof)) return false;
        Roof roof = (Roof) other;
        return this.getId() == roof.getId() &&
                this.getSquareMeterPrice() == roof.getSquareMeterPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getSquareMeterPrice());
    }

    @Override
    public String toString() {
        return "Roof{" +
                "id=" + this.id +
                ", meterPrice=" + this.SquareMeterPrice +
                '}';
    }
}