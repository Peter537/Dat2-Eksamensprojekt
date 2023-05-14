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
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CarportOrderFacade {

    public static CarportOrder getCarportOrderById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        return CarportOrderMapper.getCarportOrderById(id, connectionPool);
    }

    public static List<CarportOrder> getCarportOrdersByCustomer(Customer customer, ConnectionPool connectionPool) throws DatabaseException, NotFoundException, ValidationException {
        return CarportOrderMapper.getCarportOrdersByCustomer(customer, connectionPool);
    }

    public static List<CarportOrder> getCarportOrdersByEmployee(Employee employee, ConnectionPool connectionPool) throws DatabaseException, NotFoundException, ValidationException {
        return CarportOrderMapper.getCarportOrdersByEmployee(employee, connectionPool);
    }

    public static CarportOrder createCarportOrder(Customer customer, Address address, float width, float length, float minHeight, Roof roof, Optional<ToolRoom> toolRoom, Optional<String> remarks, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        return CarportOrderMapper.createCarportOrder(customer, address, width, length, minHeight, roof, toolRoom, remarks, connectionPool);
    }

    public static void updateOrderStatus(CarportOrder carportOrder, OrderStatus newOrderStatus, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updateOrderStatus(carportOrder, newOrderStatus, connectionPool);
    }

    public static void updateEmployee(CarportOrder carportOrder, Optional<Employee> employee, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updateEmployee(carportOrder, employee, connectionPool);
    }

    public static void updateWidth(CarportOrder carportOrder, float width, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updateWidth(carportOrder, width, connectionPool);
    }

    public static void updateLength(CarportOrder carportOrder, float length, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updateLength(carportOrder, length, connectionPool);
    }

    public static void updateMinHeight(CarportOrder carportOrder, float minHeight, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updateMinHeight(carportOrder, minHeight, connectionPool);
    }

    public static void updateToolRoom(CarportOrder carportOrder, Optional<ToolRoom> toolRoom, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updateToolRoom(carportOrder, toolRoom, connectionPool);
    }

    public static void updatePrice(CarportOrder carportOrder, Optional<Float> price, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updatePrice(carportOrder, price, connectionPool);
    }

    public static void updateRemarks(CarportOrder carportOrder, Optional<String> remarks, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updateRemarks(carportOrder, remarks, connectionPool);
    }

    public static void updateAddress(CarportOrder carportOrder, Address address, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        CarportOrderMapper.updateAddress(carportOrder, address, connectionPool);
    }
}