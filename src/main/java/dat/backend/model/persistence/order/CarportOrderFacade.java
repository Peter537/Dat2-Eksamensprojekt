package dat.backend.model.persistence.order;

import dat.backend.model.entities.CarportOrder;
import dat.backend.model.entities.OrderStatus;
import dat.backend.model.entities.Roof;
import dat.backend.model.entities.ToolRoom;
import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.List;
import java.util.Optional;

public class CarportOrderFacade {

    public static CarportOrder getCarportOrderById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return CarportOrderMapper.getCarportOrderById(id, connectionPool);
    }

    public static List<CarportOrder> getCarportByCustomerEmail(String email, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return CarportOrderMapper.getCarportByCustomerEmail(email, connectionPool);
    }

    public static CarportOrder createCarportOrder(Customer customer,
                                                  Address address,
                                                  float width,
                                                  float length,
                                                  float minHeight,
                                                  Roof roof,
                                                  ToolRoom toolRoom,
                                                  String remarks,
                                                  ConnectionPool connectionPool) throws DatabaseException {
        return CarportOrderMapper.createCarportOrder(customer, address, width, length, minHeight, roof, toolRoom, remarks, connectionPool);
    }

    public static void updateCarportOrderStatus(CarportOrder carportOrder, OrderStatus newOrderStatus, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderStatus(carportOrder, newOrderStatus, connectionPool);
    }
}