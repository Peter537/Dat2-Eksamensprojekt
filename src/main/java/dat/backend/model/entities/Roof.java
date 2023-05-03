package dat.backend.model.entities;

import java.util.Objects;

public class Roof {

    private int id;
    private float meterPrice;

    public Roof(int id, float meterPrice) {
        this.id = id;
        this.meterPrice = meterPrice;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (!(other instanceof Roof)) return false;
        Roof roof = (Roof) other;
        return this.getId() == roof.getId() &&
                this.getMeterPrice() == roof.getMeterPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getMeterPrice());
    }

    @Override
    public String toString() {
        return "Roof{" +
                "id=" + this.id +
                ", meterPrice=" + this.meterPrice +
                '}';
    }
}