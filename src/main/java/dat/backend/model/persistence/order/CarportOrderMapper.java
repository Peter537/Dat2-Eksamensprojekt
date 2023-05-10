package dat.backend.model.persistence.order;

import dat.backend.model.entities.CarportOrder;
import dat.backend.model.entities.OrderStatus;
import dat.backend.model.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CarportOrderMapper {

    static CarportOrder getCarportOrderById(int id, ConnectionPool connectionPool) {
        throw new UnsupportedOperationException();
    }

    static List<CarportOrder> getCarportByCustomerId(int customerId, ConnectionPool connectionPool) {
        return new ArrayList<>();
    }

    static List<CarportOrder> getCarportByCustomerEmail(String email, ConnectionPool connectionPool) {
        return new ArrayList<>();
    }

    static CarportOrder createCarportOrder(CarportOrder carportOrder, ConnectionPool connectionPool) {
        throw new UnsupportedOperationException();
    }

    static void updateCarportOrderStatus(CarportOrder carportOrder, OrderStatus newOrderStatus, ConnectionPool connectionPool) {
    }
}