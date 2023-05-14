package dat.backend.model.entities.item;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class ToolRoom {

    private float length;
    private float width;

    public ToolRoom(float length, float width) {
        this.length = length;
        this.width = width;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getLength() {
        return this.length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setLength(float length) {
        this.length = length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getWidth() {
        return this.width;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setWidth(float width) {
        this.width = width;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ToolRoom)) return false;
        ToolRoom toolRoom = (ToolRoom) other;
        return this.getLength() == toolRoom.getLength() &&
                this.getWidth() == toolRoom.getWidth();
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getLength(), this.getWidth());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "ToolRoom{" +
                "length='" + this.length + '\'' +
                ", width='" + this.width + '\'' +
                '}';
    }
}