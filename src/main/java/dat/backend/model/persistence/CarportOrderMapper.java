package dat.backend.model.persistence;

import dat.backend.model.entities.CarportOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CarportOrderMapper {

    static Optional<CarportOrder> getCarportOrderById(int id, ConnectionPool connectionPool) {
        return Optional.empty();
    }

    static List<CarportOrder> getCarportByCustomerId(int customerId, ConnectionPool connectionPool) {
        return new ArrayList<>();
    }

    static List<CarportOrder> getCarportByCustomerEmail(String email, ConnectionPool connectionPool) {
        return new ArrayList<>();
    }

    static CarportOrder createCarportOrder(CarportOrder carportOrder, ConnectionPool connectionPool) {
        return carportOrder;
    }
}