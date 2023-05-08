package dat.backend.model.persistence;

import dat.backend.model.entities.OrderStatus;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.OrderStatusNotFoundException;

public class OrderStatusFacade {

    public static OrderStatus getOrderStatusByStatus(String status, ConnectionPool connectionPool) throws OrderStatusNotFoundException, DatabaseException {
        return OrderStatusMapper.getOrderStatusByStatus(status, connectionPool);
    }
}