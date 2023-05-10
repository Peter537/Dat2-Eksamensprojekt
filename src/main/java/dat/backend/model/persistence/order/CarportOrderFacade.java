package dat.backend.model.persistence.order;

import dat.backend.model.entities.CarportOrder;
import dat.backend.model.entities.OrderStatus;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.List;
import java.util.Optional;

public class CarportOrderFacade {

    public static Optional<CarportOrder> getCarportOrderById(int id, ConnectionPool connectionPool) throws DatabaseException {
        return CarportOrderMapper.getCarportOrderById(id, connectionPool);
    }

    public static List<CarportOrder> getCarportByCustomerId(int customerId, ConnectionPool connectionPool) throws DatabaseException {
        return CarportOrderMapper.getCarportByCustomerId(customerId, connectionPool);
    }

    public static List<CarportOrder> getCarportByCustomerEmail(String email, ConnectionPool connectionPool) throws DatabaseException {
        return CarportOrderMapper.getCarportByCustomerEmail(email, connectionPool);
    }

    public static CarportOrder createCarportOrder(CarportOrder carportOrder, ConnectionPool connectionPool) throws DatabaseException {
        return CarportOrderMapper.createCarportOrder(carportOrder, connectionPool);
    }

    public static void updateCarportOrderStatus(CarportOrder carportOrder, OrderStatus newOrderStatus, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderStatus(carportOrder, newOrderStatus, connectionPool);
    }
}