package dat.backend.model.entities.item;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class Lumber implements Comparable<Lumber> {

    private int id;
    private int length;
    private LumberType lumberType;
    private int amount;

    public Lumber(int id, int length, LumberType lumberType, int amount) {
        this.id = id;
        this.length = length;
        this.lumberType = lumberType;
        this.amount = amount;
    }

    public float getPrice() {
        // Length is in CM, so we divide by 100 to get meters
        float lengthInMeters = this.length / 100.0f;
        return lengthInMeters * this.lumberType.getMeterPrice();
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getId() {
        return this.id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setId(int id) {
        this.id = id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getLength() {
        return this.length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setLength(int length) {
        this.length = length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public LumberType getLumberType() {
        return this.lumberType;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setLumberType(LumberType lumberType) {
        this.lumberType = lumberType;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getAmount() {
        return this.amount;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Lumber)) return false;
        Lumber lumber = (Lumber) other;
        return this.getId() == lumber.getId() &&
                this.getLength() == lumber.getLength() &&
                this.getLumberType().equals(lumber.getLumberType());
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getLength(), this.getLumberType(), this.getAmount());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Lumber{" +
                "id=" + this.id +
                ", length=" + this.length +
                ", lumberType=" + this.lumberType +
                ", amount=" + this.amount +
                '}';
    }

    @Override
    public int compareTo(Lumber other) {
        return Integer.compare(this.length, other.length);
    }
}