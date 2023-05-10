package dat.backend.model.entities.user;

import java.util.Objects;

public class Position {

    private String positionName;

    public Position(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position)) return false;
        Position position = (Position) other;
        return this.getPositionName().equals(position.getPositionName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getPositionName());
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionName='" + this.positionName + '\'' +
                '}';
    }
}