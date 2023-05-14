package dat.backend.model.entities.order;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public class OrderStatus {

    private String status;
    private String displayName;
    private int sortValue;

    public OrderStatus(String status, String displayName, int sortValue) {
        this.status = status;
        this.displayName = displayName;
        this.sortValue = sortValue;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getStatus() {
        return this.status;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setStatus(String status) {
        this.status = status;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public String getDisplayName() {
        return this.displayName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getSortValue() {
        return this.sortValue;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setSortValue(int sortValue) {
        this.sortValue = sortValue;
    }

    @IgnoreCoverage(reason = "equals")
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof OrderStatus)) return false;
        OrderStatus orderStatus = (OrderStatus) other;
        return this.getStatus().equals(orderStatus.getStatus()) &&
                this.getSortValue() == orderStatus.getSortValue();
    }

    @IgnoreCoverage(reason = "hashCode")
    @Override
    public int hashCode() {
        return Objects.hash(this.getStatus(), this.getDisplayName(), this.getSortValue());
    }

    @IgnoreCoverage(reason = "toString")
    @Override
    public String toString() {
        return "OrderStatus{" +
                "status='" + this.status + '\'' +
                ", displayName='" + this.displayName + '\'' +
                ", sortValue=" + this.sortValue +
                '}';
    }
}