package dat.backend.model.entities.user;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class Position {

    private String positionName;

    public Position(String positionName) {
        this.positionName = positionName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getPositionName() {
        return this.positionName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position)) return false;
        Position position = (Position) other;
        return this.getPositionName().equals(position.getPositionName());
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getPositionName());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "Position{" +
                "positionName='" + this.positionName + '\'' +
                '}';
    }
}