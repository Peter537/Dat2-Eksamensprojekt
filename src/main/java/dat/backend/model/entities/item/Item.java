package dat.backend.model.entities.item;

import dat.backend.annotation.IgnoreCoverage;

import java.util.Objects;

public abstract class Item {

    private int id;

    public Item(int id) {
        this.id = id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getId() {
        return id;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setId(int id) {
        this.id = id;
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
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + this.getId() +
                '}';
    }
}