package dat.backend.model.persistence.order;

import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.order.OrderStatus;
import dat.backend.model.entities.user.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
class CarportOrderMapper {

    /**
     * This method will retrieve a CarportOrder from an id
     *
     * @param id             The id to search for
     * @param connectionPool Connection pool
     * @return The CarportOrder object
     * @throws DatabaseException if an error occurs while communicating with the database
     * @throws NotFoundException if the id does not exist
     */
    static CarportOrder getCarportOrderById(int id, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        String query = "SELECT * FROM carportorderWithAll WHERE carportorderWithAll.id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                return createCarportOrderFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error while getting CarportOrder with id " + id);
        }
    }

    /**
     * This method will retrieve all Carport orders from a customer
     *
     * @param customer       The customer to search for
     * @param connectionPool Connection pool
     * @return A list of CarportOrder objects
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static List<CarportOrder> getCarportOrdersByCustomer(Customer customer, ConnectionPool connectionPool) throws DatabaseException {
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
        } catch (SQLException | DatabaseException | NotFoundException e) {
            throw new DatabaseException(e, "Error while getting CarportOrder with customer email " + customer.getEmail());
        }

        return carportOrders;
    }

    /**
     * This method will retrieve all Carport orders from an employee
     *
     * @param employee       The employee to search for
     * @param connectionPool Connection pool
     * @return A list of CarportOrder objects
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static List<CarportOrder> getCarportOrdersByEmployee(Employee employee, ConnectionPool connectionPool) throws DatabaseException {
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
        } catch (SQLException | DatabaseException | NotFoundException e) {
            throw new DatabaseException(e, "Error while getting CarportOrder with employee email " + employee.getEmail());
        }

        return carportOrders;
    }

    /**
     * This method will retrieve all Carport orders
     *
     * @param connectionPool Connection pool
     * @return A list of CarportOrder objects
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static List<CarportOrder> getAllCarportOrders(ConnectionPool connectionPool) throws DatabaseException {
        List<CarportOrder> carportOrders = new ArrayList<>();
        String query = "SELECT * FROM carport_order";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    CarportOrder carportOrder = getCarportOrderById(id, connectionPool);
                    carportOrders.add(carportOrder);
                }
            }
        } catch (SQLException | DatabaseException | NotFoundException e) {
            throw new DatabaseException(e, "Error while getting all CarportOrders");
        }

        return carportOrders;
    }

    /**
     * This method will get the last newly carport orders to show on the employee front page
     *
     * @param connectionPool Connection pool
     * @return A list of CarportOrder objects
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static List<CarportOrder> getCarportOrdersAsNews(ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT id, created_on, price_from_partslist FROM carport_order ORDER BY created_on DESC LIMIT 6";
        List<CarportOrder> carportOrders = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Date createdOn = resultSet.getDate("created_on");
                    float price = resultSet.getFloat("price_from_partslist");
                    CarportOrder carportOrder = new CarportOrder(id, null, null, null, null, null, null, Float.NaN, Float.NaN, Float.NaN, null, Optional.of(price), Float.NaN);
                    carportOrder.setCreatedOn(createdOn);
                    carportOrders.add(carportOrder);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error while getting all CarportOrders");
        }

        return carportOrders;
    }

    /**
     * This method will retrieve the last OrderStatus from a Customer
     *
     * @param customer       The customer to search for
     * @param connectionPool Connection pool
     * @return An optional OrderStatus object, empty if no order status was found
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static Optional<OrderStatus> getLatestOrderStatusFromCustomer(Customer customer, ConnectionPool connectionPool) throws DatabaseException {
        String query = "SELECT co.orderstatus, o.displayname, o.sortvalue FROM carport_order co JOIN orderstatus o on co.orderstatus = o.status WHERE co.fk_customer_email = ? ORDER BY created_on DESC LIMIT 1";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, customer.getEmail());
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                String orderStatus = resultSet.getString("orderstatus");
                String displayName = resultSet.getString("displayname");
                int sortValue = resultSet.getInt("sortvalue");
                return Optional.of(new OrderStatus(orderStatus, displayName, sortValue));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error while getting latest order status");
        }
    }

    /**
     * This method will create a new carport order from the arguments
     *
     * @param customer           The customer who ordered the carport
     * @param address            The address where the carport should be delivered
     * @param width              The width of the carport
     * @param length             The length of the carport
     * @param minHeight          The minimum height of the carport
     * @param roof               The roof of the carport
     * @param toolRoom           The tool room of the carport
     * @param remarks            Remarks about the carport
     * @param priceFromPartsList The price of the carport
     * @param connectionPool     Connection pool
     * @return The newly created carport order
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static CarportOrder create(Customer customer, Address address, float width, float length, float minHeight, Roof roof, Optional<ToolRoom> toolRoom, Optional<String> remarks, float priceFromPartsList, ConnectionPool connectionPool) throws DatabaseException {
        String query = "INSERT INTO carport_order (fk_customer_email, address, zipcode, width, length, min_height, fk_roof_id, toolroom_width, toolroom_length, price_from_partslist, remarks, orderstatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

                statement.setFloat(10, priceFromPartsList);
                if (remarks.isPresent()) {
                    statement.setString(11, remarks.get());
                } else {
                    statement.setNull(11, Types.VARCHAR);
                }

                statement.setString(12, "ORDER_CREATED");
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

    /**
     * This method is for employees to claim a carport order
     *
     * @param carportOrder   The carport order to claim
     * @param employee       The employee who claims the carport order
     * @param connectionPool Connection pool
     * @return The updated carport order
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static CarportOrder claim(CarportOrder carportOrder, Employee employee, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE carport_order SET fk_employee_email = ?, orderstatus = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, employee.getEmail());
                statement.setString(2, "ORDER_EMPLOYEE_ASSIGNED");
                statement.setInt(3, carportOrder.getId());
                statement.executeUpdate();
                return getCarportOrderById(carportOrder.getId(), connectionPool);
            }
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Error while claiming CarportOrder with id " + carportOrder.getId());
        }
    }

    /**
     * This method is for employees to make an offer for a carport order
     *
     * @param carportOrder   The carport order to make an offer for
     * @param price          The price of the offer
     * @param connectionPool Connection pool
     * @return The updated carport order
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static CarportOrder makeOffer(CarportOrder carportOrder, float price, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE carport_order SET price = ?, orderstatus = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setFloat(1, price);
                statement.setString(2, "ORDER_OFFER_GIVEN");
                statement.setInt(3, carportOrder.getId());
                statement.executeUpdate();
                return getCarportOrderById(carportOrder.getId(), connectionPool);
            }
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Error while making offer for CarportOrder with id " + carportOrder.getId());
        }
    }

    /**
     * This method is for customers to accept an offer for a carport order
     *
     * @param carportOrder   The carport order to accept an offer for
     * @param connectionPool Connection pool
     * @return The updated carport order
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static CarportOrder acceptOffer(CarportOrder carportOrder, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE carport_order SET orderstatus = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "ORDER_OFFER_ACCEPTED");
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                return getCarportOrderById(carportOrder.getId(), connectionPool);
            }
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Error while accepting offer for CarportOrder with id " + carportOrder.getId());
        }
    }

    /**
     * This method is for customers for cancelling an order
     *
     * @param carportOrder   The carport order to cancel
     * @param connectionPool Connection pool
     * @return The updated carport order
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static CarportOrder cancelOrder(CarportOrder carportOrder, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE carport_order SET orderstatus = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "ORDER_CANCELLED");
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                return getCarportOrderById(carportOrder.getId(), connectionPool);
            }
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Error while attempting to cancel the order with the ID: " + carportOrder.getId());
        }
    }

    /**
     * This method is for employees to set a carport order to ready for delivery
     *
     * @param carportOrder   The carport order to ready
     * @param connectionPool Connection pool
     * @return The updated carport order
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static CarportOrder ready(CarportOrder carportOrder, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE carport_order SET orderstatus = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "ORDER_READY");
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                return getCarportOrderById(carportOrder.getId(), connectionPool);
            }
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Error while setting CarportOrder with id " + carportOrder.getId() + " to ready");
        }
    }

    /**
     * This method is for employees to set a carport order to delivered
     *
     * @param carportOrder   The carport order to deliver
     * @param connectionPool Connection pool
     * @return The updated carport order
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static CarportOrder deliver(CarportOrder carportOrder, ConnectionPool connectionPool) throws DatabaseException {
        String query = "UPDATE carport_order SET orderstatus = ? WHERE id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "ORDER_DELIVERED");
                statement.setInt(2, carportOrder.getId());
                statement.executeUpdate();
                return getCarportOrderById(carportOrder.getId(), connectionPool);
            }
        } catch (SQLException | NotFoundException e) {
            throw new DatabaseException(e, "Error while setting CarportOrder with id " + carportOrder.getId() + " to delivered");
        }
    }

    /**
     * This method will update the width of a carport order
     *
     * @param carportOrder   The carport order to update
     * @param width          The new width
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateWidth(CarportOrder carportOrder, float width, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the length of a carport order
     *
     * @param carportOrder   The carport order to update
     * @param length         The new length
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateLength(CarportOrder carportOrder, float length, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the minimum height of a carport order
     *
     * @param carportOrder   The carport order to update
     * @param minHeight      The new minimum height
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateMinHeight(CarportOrder carportOrder, float minHeight, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the toolroom of a carport order
     *
     * @param carportOrder   The carport order to update
     * @param toolRoom       The new toolroom
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateToolRoom(CarportOrder carportOrder, Optional<ToolRoom> toolRoom, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the price of a carport order
     *
     * @param carportOrder   The carport order to update
     * @param price          The new price
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updatePrice(CarportOrder carportOrder, Optional<Float> price, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will update the address of a carport order
     *
     * @param carportOrder   The carport order to update
     * @param address        The new address
     * @param connectionPool Connection pool
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    static void updateAddress(CarportOrder carportOrder, Address address, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * This method will create a carport order from a result set
     *
     * @param resultSet The result set to create the carport order from
     * @return The created carport order
     * @throws SQLException      If an error occurs while communicating with the database
     * @throws NotFoundException If the carport order was not found
     */
    private static CarportOrder createCarportOrderFromResultSet(ResultSet resultSet) throws SQLException, NotFoundException {
        if (!resultSet.next()) {
            throw new NotFoundException("CarportOrder not found");
        }

        int id = resultSet.getInt("id");
        OrderStatus orderStatus = new OrderStatus(resultSet.getString("orderstatus"), resultSet.getString("displayname"), resultSet.getInt("sortvalue"));
        Address address = new Address(resultSet.getString("address"), new Zip(resultSet.getInt("zipcode"), resultSet.getString("city_name")));

        Optional<Employee> employee = createEmployeeFromCarportOrderResulSet(resultSet);
        Customer customer = createCustomerFromCarportOrderResultSet(resultSet);

        Roof roof = new Roof(resultSet.getInt("fk_roof_id"), resultSet.getFloat("squaremeter_price"), resultSet.getString("type"), resultSet.getString("roofdisplayname"));
        float width = resultSet.getFloat("width");
        float length = resultSet.getFloat("length");
        float minHeight = resultSet.getFloat("min_height");
        float toolRoomWidth = resultSet.getFloat("toolroom_width");
        float toolRoomLength = resultSet.getFloat("toolroom_length");
        Optional<ToolRoom> toolRoom = Optional.empty();
        if (toolRoomWidth != 0 && toolRoomLength != 0) {
            toolRoom = Optional.of(new ToolRoom(toolRoomWidth, toolRoomLength));
        }

        float priceFromPartsList = resultSet.getFloat("price_from_partslist");
        Optional<String> remarks = Optional.ofNullable(resultSet.getString("remarks"));
        float priceDb = resultSet.getFloat("price");
        Optional<Float> price = Optional.empty();
        if (priceDb != 0) {
            price = Optional.of(priceDb);
        }

        return new CarportOrder(id, address, employee, customer, orderStatus, roof, remarks, length, width, minHeight, toolRoom, price, priceFromPartsList);
    }

    /**
     * This method will create a customer from a result set
     *
     * @param resultSet The result set to create the customer from
     * @return The created customer
     * @throws SQLException If an error occurs while communicating with the database
     */
    private static Customer createCustomerFromCarportOrderResultSet(ResultSet resultSet) throws SQLException {
        int customerId = resultSet.getInt("customerid");
        String customerName = resultSet.getString("customername");
        String customerEmail = resultSet.getString("fk_customer_email");
        Optional<String> customerPhone = Optional.ofNullable(resultSet.getString("phonenumber"));
        Optional<Address> address1 = createCustomerAddressFromCarportOrderResultSet(1, resultSet);
        Optional<Address> address2 = createCustomerAddressFromCarportOrderResultSet(2, resultSet);
        Optional<Address> address3 = createCustomerAddressFromCarportOrderResultSet(3, resultSet);
        return new Customer(customerId, customerEmail, customerName, customerPhone, address1, address2, address3);
    }

    /**
     * This method will create an employee from a result set
     *
     * @param resultSet The result set to create the employee from
     * @return The created employee
     * @throws SQLException If an error occurs while communicating with the database
     */
    private static Optional<Employee> createEmployeeFromCarportOrderResulSet(ResultSet resultSet) throws SQLException {
        String employeeEmail = resultSet.getString("fk_employee_email");
        if (employeeEmail == null) {
            return Optional.empty();
        }

        //Generate employee object
        int employeeId = resultSet.getInt("employeeid");
        String employeeName = resultSet.getString("employeename");
        Optional<String> privatePhonenumber = Optional.ofNullable(resultSet.getString("private_phonenumber"));
        Optional<String> workPhonenumber = Optional.ofNullable(resultSet.getString("work_phonenumber"));
        Position position = new Position(resultSet.getString("fk_position"));

        //Generate department object for employee
        int departmentId = resultSet.getInt("departmentid");
        String departmentName = resultSet.getString("departmentname");
        Zip departmentZip = new Zip(resultSet.getInt("departmentzip"), resultSet.getString("departmentcity"));
        Address departmentAddress = new Address(resultSet.getString("departmentaddress"), departmentZip);
        Department department = new Department(departmentId, departmentName, departmentAddress);

        return Optional.of(new Employee(employeeId, employeeEmail, employeeName, privatePhonenumber, workPhonenumber, position, department));
    }

    /**
     * This method will create a customers address from a result set
     *
     * @param addressNumber The address number to create
     * @param resultSet     The result set to create the address from
     * @return The created address
     * @throws SQLException If an error occurs while communicating with the database
     */
    private static Optional<Address> createCustomerAddressFromCarportOrderResultSet(int addressNumber, ResultSet resultSet) throws SQLException {
        String address = resultSet.getString("address_" + addressNumber);
        int zipCode = resultSet.getInt("zipcode_" + addressNumber);
        if (address == null || zipCode == 0) {
            return Optional.empty();
        }

        String city = resultSet.getString("city_" + addressNumber);
        return Optional.of(new Address(address, new Zip(zipCode, city)));
    }
}