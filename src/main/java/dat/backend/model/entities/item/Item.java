package dat.backend.model.entities.item;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class Item {

    private int id;
    private Optional<Integer> price;

    public Item(int id, Optional<Integer> price) {
        this.id = id;
        this.price = price;
    }

    public Item(int id) {
        this(id, Optional.empty());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Optional<Integer> getPrice() {
        return this.price;
    }

    public void setPrice(Optional<Integer> price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Item)) return false;
        Item item = (Item) other;
        return this.getId() == item.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getPrice());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + this.id +
                ", price=" + this.price +
                '}';
    }
}