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
import dat.backend.model.persistence.item.RoofFacade;
import dat.backend.model.persistence.user.CustomerFacade;
import dat.backend.model.persistence.user.EmployeeFacade;
import dat.backend.model.persistence.user.ZipFacade;
import dat.backend.model.services.Validation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
class CarportOrderMapper {

    static CarportOrder getCarportOrderById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM carport_order WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                return createCarportOrderFromResultSet(resultSet, connectionPool);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error while getting CarportOrder with id " + id);
        }
    }

    static List<CarportOrder> getCarportOrdersByCustomer(Customer customer, ConnectionPool connectionPool) throws DatabaseException, NotFoundException, ValidationException {
        Validation.validateCustomer(customer);
        List<CarportOrder> carportOrders = new ArrayList<>();
        String query = "SELECT * FROM carport_order WHERE fk_customer_email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, customer.getEmail());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    CarportOrder carportOrder = getCarportOrderById(id, connectionPool);
                    carportOrders.add(carportOrder);
                }
            }
        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException(e, "Error while getting CarportOrder with customer email " + customer.getEmail());
        }

        return carportOrders;
    }

    static List<CarportOrder> getCarportOrdersByEmployee(Employee employee, ConnectionPool connectionPool) throws DatabaseException, NotFoundException, ValidationException {
        Validation.validateEmployee(employee);
        List<CarportOrder> carportOrders = new ArrayList<>();
        String query = "SELECT * FROM carport_order WHERE fk_employee_email = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, employee.getEmail());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    CarportOrder carportOrder = getCarportOrderById(id, connectionPool);
                    carportOrders.add(carportOrder);
                }
            }
        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException(e, "Error while getting CarportOrder with employee email " + employee.getEmail());
        }

        return carportOrders;
    }

    static CarportOrder createCarportOrder(Customer customer, Address address, float width, float length, float minHeight, Roof roof, Optional<ToolRoom> toolRoom, Optional<String> remarks, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCreateCarportOrder(customer, address, width, length, minHeight, roof, toolRoom, remarks);
        String query = "INSERT INTO carport_order (fk_customer_email, address, zipcode, width, length, min_height, fk_roof_id, toolroom_width, toolroom_length, remarks, orderstatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, customer.getEmail());
                statement.setString(2, address.getStreet());
                statement.setInt(3, address.getZip().getZipCode());
                statement.setFloat(4, width);
                statement.setFloat(5, length);
                statement.setFloat(6, minHeight);
                statement.setInt(7, roof.getId());
                if (toolRoom.isPresent()) {
                    statement.setFloat(8, toolRoom.get().getWidth());
                    statement.setFloat(9, toolRoom.get().getLength());
                } else {
                    statement.setNull(8, Types.FLOAT);
                    statement.setNull(9, Types.FLOAT);
                }

                if (remarks.isPresent()) {
                    statement.setString(10, remarks.get());
                } else {
                    statement.setNull(10, Types.VARCHAR);
                }
                statement.setString(11, OrderStatusFacade.getOrderStatusByStatus("ORDER_CREATED", connectionPool).getStatus());
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating carport order failed, no rows affected.");
                }

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new SQLException("Creating carport order failed, no ID obtained.");
                }

                int id = generatedKeys.getInt(1);
                return getCarportOrderById(id, connectionPool);
            }
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Could not create order");
        }
    }

    static void updateCarportOrderStatus(CarportOrder carportOrder, OrderStatus newOrderStatus, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        Validation.validateOrderStatus(newOrderStatus);
        String query = "UPDATE carport_order SET orderstatus = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, newOrderStatus.getStatus());
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setOrderStatus(newOrderStatus);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order status");
        }
    }

    static void updateCarportOrderEmployee(CarportOrder carportOrder, Optional<Employee> employee, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        if (employee.isPresent()) {
            Validation.validateEmployee(employee.get());
        }

        String query = "UPDATE carport_order SET fk_employee_email = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (employee.isPresent()) {
                    statement.setString(1, employee.get().getEmail());
                } else {
                    statement.setNull(1, Types.VARCHAR);
                }
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setEmployee(employee);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order employee");
        }
    }

    static void updateCarportOrderWidth(CarportOrder carportOrder, float width, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        Validation.validateWidth(width);
        String query = "UPDATE carport_order SET width = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setFloat(1, width);
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setWidth(width);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order width");
        }
    }

    static void updateCarportOrderLength(CarportOrder carportOrder, float length, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        Validation.validateLength(length);
        String query = "UPDATE carport_order SET length = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setFloat(1, length);
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setLength(length);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order length");
        }
    }

    static void updateCarportOrderMinHeight(CarportOrder carportOrder, float minHeight, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        Validation.validateMinHeight(minHeight);
        String query = "UPDATE carport_order SET min_height = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setFloat(1, minHeight);
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setMinHeight(minHeight);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order min height");
        }
    }

    static void updateCarportOrderToolRoom(CarportOrder carportOrder, Optional<ToolRoom> toolRoom, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        if (toolRoom.isPresent()) {
            Validation.validateToolRoom(toolRoom.get());
        }

        String query = "UPDATE carport_order SET toolroom_width = ?, toolroom_length = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (toolRoom.isPresent()) {
                    statement.setFloat(1, toolRoom.get().getWidth());
                    statement.setFloat(2, toolRoom.get().getLength());
                } else {
                    statement.setNull(1, Types.FLOAT);
                    statement.setNull(2, Types.FLOAT);
                }
                statement.setInt(3, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setToolRoom(toolRoom);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order tool room width");
        }
    }

    static void updateCarportOrderPrice(CarportOrder carportOrder, Optional<Float> price, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        if (price.isPresent()) {
            Validation.validatePrice(price.get());
        }

        String query = "UPDATE carport_order SET price = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (price.isPresent()) {
                    statement.setFloat(1, price.get());
                } else {
                    statement.setNull(1, Types.FLOAT);
                }
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setPrice(price);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order price");
        }
    }

    static void updateCarportOrderRemarks(CarportOrder carportOrder, Optional<String> remarks, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        if (remarks.isPresent()) {
            Validation.validateRemarks(remarks.get());
        }

        String query = "UPDATE carport_order SET remarks = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (remarks.isPresent()) {
                    statement.setString(1, remarks.get());
                } else {
                    statement.setNull(1, Types.VARCHAR);
                }
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setRemarks(remarks);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order remarks");
        }
    }

    static void updateCarportOrderAddress(CarportOrder carportOrder, Address address, ConnectionPool connectionPool) throws DatabaseException, ValidationException {
        Validation.validateCarportOrder(carportOrder);
        Validation.validateAddress(address);
        String query = "UPDATE carport_order SET address = ?, zipcode = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, address.getStreet());
                statement.setInt(2, address.getZip().getZipCode());
                statement.setInt(3, carportOrder.getId());
                statement.executeUpdate();
                carportOrder.setAddress(address);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not update carport order address");
        }
    }

    private static CarportOrder createCarportOrderFromResultSet(ResultSet resultSet, ConnectionPool connectionPool) throws SQLException, NotFoundException, DatabaseException {
        if (!resultSet.next()) {
            throw new NotFoundException("CarportOrder not found");
        }

        int id = resultSet.getInt("id");
        OrderStatus orderStatus = OrderStatusFacade.getOrderStatusByStatus(resultSet.getString("orderstatus"), connectionPool);
        Address address = new Address(resultSet.getString("address"), ZipFacade.getZipByZipCode(resultSet.getInt("zipcode"), connectionPool));
        String employeeEmail = resultSet.getString("fk_employee_email");
        Optional<Employee> employee = Optional.empty();
        if (employeeEmail != null) {
            employee = Optional.of(EmployeeFacade.getEmployeeByEmail(employeeEmail, connectionPool));
        }

        Customer customer = CustomerFacade.getCustomerByEmail(resultSet.getString("fk_customer_email"), connectionPool);
        Roof roof = RoofFacade.getRoofById(resultSet.getInt("fk_roof_id"), connectionPool);
        float width = resultSet.getFloat("width");
        float length = resultSet.getFloat("length");
        float minHeight = resultSet.getFloat("min_height");
        float toolRoomWidth = resultSet.getFloat("toolroom_width");
        float toolRoomLength = resultSet.getFloat("toolroom_length");
        Optional<ToolRoom> toolRoom = Optional.empty();
        if (toolRoomWidth != 0 && toolRoomLength != 0) {
            toolRoom = Optional.of(new ToolRoom(toolRoomWidth, toolRoomLength));
        }

        Optional<String> remarks = Optional.ofNullable(resultSet.getString("remarks"));
        float priceDb = resultSet.getFloat("price");
        Optional<Float> price = Optional.empty();
        if (priceDb != 0) {
            price = Optional.of(priceDb);
        }

        return new CarportOrder(id, address, employee, customer, orderStatus, roof, remarks, length, width, minHeight, toolRoom, price);
    }
}