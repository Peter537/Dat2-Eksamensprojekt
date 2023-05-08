package dat.backend.model.entities;

public abstract class Item {
    private int id;
    private int price;

    public Item(int id, int price) {
        this.id = id;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
