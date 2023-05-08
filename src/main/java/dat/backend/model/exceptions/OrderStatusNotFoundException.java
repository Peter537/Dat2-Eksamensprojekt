package dat.backend.model.exceptions;

public class OrderStatusNotFoundException extends Exception {

    public OrderStatusNotFoundException(String message) {
        super(message);
    }
}