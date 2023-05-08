package dat.backend.model.entities;

import java.util.Objects;

public class OrderStatus {

    private String name;
    private int sortValue;

    OrderStatus(String name, int sortValue) {
        this.name = name;
        this.sortValue = sortValue;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortValue() {
        return this.sortValue;
    }

    public void setSortValue(int sortValue) {
        this.sortValue = sortValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getSortValue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof OrderStatus)) return false;
        OrderStatus orderStatus = (OrderStatus) other;
        return this.getName().equals(orderStatus.getName()) &&
                this.getSortValue() == orderStatus.getSortValue();
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "name='" + this.name + '\'' +
                ", sortValue=" + this.sortValue +
                "}";
    }
}