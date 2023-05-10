package dat.backend.model.persistence.order;

import dat.backend.model.entities.OrderStatus;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.OrderStatusNotFoundException;
import dat.backend.model.persistence.ConnectionPool;

public class OrderStatusFacade {

    public static OrderStatus getOrderStatusByStatus(String status, ConnectionPool connectionPool) throws OrderStatusNotFoundException, DatabaseException {
        return OrderStatusMapper.getOrderStatusByStatus(status, connectionPool);
    }
}