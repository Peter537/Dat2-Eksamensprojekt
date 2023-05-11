package dat.backend.model.entities.item;

import java.util.Objects;

public class Roof extends Item {

    private float squareMeterPrice;
    private String type;

    public Roof(int id, float squareMeterPrice, String type) {
        super(id);
        this.squareMeterPrice = squareMeterPrice;
        this.type = type;
    }

    public float getSquareMeterPrice() {
        return this.squareMeterPrice;
    }

    public void setSquareMeterPrice(float squareMeterPrice) {
        this.squareMeterPrice = squareMeterPrice;
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
        return super.equals(other) &&
                this.getSquareMeterPrice() == roof.getSquareMeterPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getSquareMeterPrice());
    }

    @Override
    public String toString() {
        return "Roof{" +
                "id=" + this.getId() +
                ", meterPrice=" + this.squareMeterPrice +
                ", type='" + this.type + '\'' +
                '}';
    }
}