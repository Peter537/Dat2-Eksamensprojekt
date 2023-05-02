package dat.backend.model.entities;

import java.util.Objects;

public class Lumber {

    private int id;
    private int length;
    private LumberType lumberType;

    public Lumber(int id, int length, LumberType lumberType) {
        this.id = id;
        this.length = length;
        this.lumberType = lumberType;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Lumber)) return false;
        Lumber lumber = (Lumber) other;
        return this.getId() == lumber.getId() &&
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
                "id=" + this.id +
                ", length=" + this.length +
                ", lumberType=" + this.lumberType +
                '}';
    }
}