package dat.backend.model.entities.item;

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

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LumberType getLumberType() {
        return this.lumberType;
    }

    public void setLumberType(LumberType lumberType) {
        this.lumberType = lumberType;
    }

    public int getAmount() {
    	return this.amount;
    }

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