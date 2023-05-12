package dat.backend.model.persistence.order;

import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.order.OrderStatus;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CarportOrderFacade {

    public static CarportOrder getCarportOrderById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return CarportOrderMapper.getCarportOrderById(id, connectionPool);
    }

    public static List<CarportOrder> getCarportByCustomerEmail(String email, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return CarportOrderMapper.getCarportByCustomerEmail(email, connectionPool);
    }

    public static CarportOrder createCarportOrder(Customer customer, Address address, float width, float length, float minHeight, Roof roof, Optional<ToolRoom> toolRoom, Optional<String> remarks, ConnectionPool connectionPool) throws DatabaseException {
        return CarportOrderMapper.createCarportOrder(customer, address, width, length, minHeight, roof, toolRoom, remarks, connectionPool);
    }

    public static void updateCarportOrderStatus(CarportOrder carportOrder, OrderStatus newOrderStatus, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderStatus(carportOrder, newOrderStatus, connectionPool);
    }

    public static void updateCarportOrderEmployee(CarportOrder carportOrder, Optional<Employee> employee, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderEmployee(carportOrder, employee, connectionPool);
    }

    public static void updateCarportOrderWidth(CarportOrder carportOrder, float width, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderWidth(carportOrder, width, connectionPool);
    }

    public static void updateCarportOrderLength(CarportOrder carportOrder, float length, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderLength(carportOrder, length, connectionPool);
    }

    public static void updateCarportOrderMinHeight(CarportOrder carportOrder, float minHeight, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderMinHeight(carportOrder, minHeight, connectionPool);
    }

    public static void updateCarportOrderToolRoom(CarportOrder carportOrder, Optional<ToolRoom> toolRoom, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderToolRoom(carportOrder, toolRoom, connectionPool);
    }

    public static void updateCarportOrderPrice(CarportOrder carportOrder, Optional<Float> price, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderPrice(carportOrder, price, connectionPool);
    }

    public static void updateCarportOrderRemarks(CarportOrder carportOrder, Optional<String> remarks, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderRemarks(carportOrder, remarks, connectionPool);
    }

    public static void updateCarportOrderAddress(CarportOrder carportOrder, Address address, ConnectionPool connectionPool) throws DatabaseException {
        CarportOrderMapper.updateCarportOrderAddress(carportOrder, address, connectionPool);
    }
}