package dat.backend.model.entities;

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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getSortValue() {
        return this.sortValue;
    }

    public void setSortValue(int sortValue) {
        this.sortValue = sortValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getStatus(), this.getDisplayName(), this.getSortValue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof OrderStatus)) return false;
        OrderStatus orderStatus = (OrderStatus) other;
        return this.getStatus().equals(orderStatus.getStatus()) &&
                this.getSortValue() == orderStatus.getSortValue();
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "status='" + this.status + '\'' +
                ", displayName='" + this.displayName + '\'' +
                ", sortValue=" + this.sortValue +
                '}';
    }
}