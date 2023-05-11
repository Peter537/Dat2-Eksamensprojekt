package dat.backend.model.entities;

import java.util.Objects;

public class ToolRoom {

    private float length;
    private float width;

    public ToolRoom(float length, float width) {
        this.length = length;
        this.width = width;
    }

    public float getLength() {
        return this.length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ToolRoom)) return false;
        ToolRoom toolRoom = (ToolRoom) other;
        return this.getLength() == toolRoom.getLength() &&
                this.getWidth() == toolRoom.getWidth();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLength(), this.getWidth());
    }

    @Override
    public String toString() {
        return "ToolRoom{" +
                "length='" + this.length + '\'' +
                ", width='" + this.width + '\'' +
                '}';
    }
}