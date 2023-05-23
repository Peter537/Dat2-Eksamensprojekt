package dat.backend.model.entities.item;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Lumber implements Comparable<Lumber> {

    private final int id;
    private final int length;
    private final LumberType lumberType;
    private final int amount;

    private Optional<String> description;

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
    public int getLength() {
        return this.length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public LumberType getLumberType() {
        return this.lumberType;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getAmount() {
        return this.amount;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Optional<String> getDescription() {
        return this.description;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setDescription(Optional<String> description) {
        this.description = description;
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