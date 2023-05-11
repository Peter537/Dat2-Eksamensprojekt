package dat.backend.model.entities.item;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;
import java.util.Optional;

public class Lumber extends Item implements Comparable<Lumber> {

    private int length;
    private LumberType lumberType;
    private int amount;

    public Lumber(int id, int length, LumberType lumberType, Integer price, int amount) {
        super(id, Optional.ofNullable(price));
        this.length = length;
        this.lumberType = lumberType;
        this.amount = amount;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getLength() {
        return length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setLength(int length) {
        this.length = length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public LumberType getLumberType() {
        return lumberType;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setLumberType(LumberType lumberType) {
        this.lumberType = lumberType;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getAmount() {
        return amount;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Lumber)) return false;
        Lumber lumber = (Lumber) other;
        return super.equals(other) &&
                this.getLength() == lumber.getLength() &&
                this.getLumberType().equals(lumber.getLumberType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getLength(), this.getLumberType());
    }

    @Override
    public String toString() {
        return "Lumber{" +
                "id=" + this.getId() +
                ", length=" + this.length +
                ", lumberType=" + this.lumberType +
                ", amount=" + this.amount +
                ", price=" + this.getPrice() +
                '}';
    }

    @Override
    public int compareTo(Lumber other) {
        return Integer.compare(this.length, other.length);
    }
}