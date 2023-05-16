package dat.backend.model.persistence.order;

import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.item.ToolRoom;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.entities.order.OrderStatus;
import dat.backend.model.entities.user.Address;
import dat.backend.model.entities.user.Customer;
import dat.backend.model.entities.user.Employee;
import dat.backend.model.entities.user.Zip;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.exceptions.ValidationException;
import dat.backend.model.persistence.TestDatabase;
import dat.backend.model.persistence.item.RoofFacade;
import dat.backend.model.persistence.user.CustomerFacade;
import dat.backend.model.persistence.user.EmployeeFacade;
import dat.backend.model.persistence.user.ZipFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CarportOrderMapperTest extends TestDatabase {

    @BeforeEach
    public void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM employee");
                stmt.execute("DELETE FROM department");
                stmt.execute("DELETE FROM zip");
                stmt.execute("DELETE FROM position");
                stmt.execute("DELETE FROM orderstatus");
                stmt.execute("DELETE FROM customer");
                stmt.execute("DELETE FROM carport_order");
                stmt.execute("DELETE FROM roof");
                stmt.execute("DELETE FROM type");
                stmt.execute("ALTER TABLE roof AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE customer AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE employee AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE department AUTO_INCREMENT = 1;");
                stmt.execute("ALTER TABLE carport_order AUTO_INCREMENT = 1;");

                // Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO employee (email, name, password, fk_position, fk_department_id) " +
                        "VALUES ('ben@johannesfog.dk', 'ben', '123', 'Sales', 1), ('allan@johannesfog.dk', 'allan', '1234', 'Sales', 1), ('alex@johannesfog.dk', 'alex', '12345', 'Sales', 1)");
                stmt.execute("INSERT INTO department (address, zipcode, name) VALUES ('Lyngby Adresse', 2800, 'Lyngby Trælast'), ('Lyngby Adresse 2', 2800, 'Lyngby Trælast 2')");
                stmt.execute("INSERT INTO customer (email, name, password) " +
                        "VALUES ('ben@gmail.com', 'ben', '123'), ('allan@outlook.dk', 'allan', '1234'), ('alex@hotmail.com', 'alex', '12345')");
                stmt.execute("INSERT INTO zip (zipcode, city_name)" +
                        " VALUES ('2800', 'Lyngby'), ('2730', 'Herlev')");
                stmt.execute("INSERT INTO type (type, displayname) " +
                        "VALUES ('PLASTIC_ROOF', 'Plastic tag'), ('TILED_ROOF', 'Tegl tag')");
                stmt.execute("INSERT INTO roof (squaremeter_price, type) " +
                        "VALUES (100, 'PLASTIC_ROOF'), (200, 'TILED_ROOF')");
                stmt.execute("INSERT INTO position (position) VALUES ('Sales'), ('CEO')");
                stmt.execute("INSERT INTO orderstatus (status, sortvalue, displayname) " +
                        "VALUES ('ORDER_CREATED', 1, 'Ordre oprettet'), ('ORDER_ACCEPTED', 2, 'Ordre accepteret'), ('ORDER_COMPLETED', 3, 'Ordre afsluttet'), ('ORDER_CANCELLED', 4, 'Ordre annulleret')");
                stmt.execute("INSERT INTO carport_order (orderstatus, address, zipcode, fk_employee_email, fk_customer_email, fk_roof_id, width, length, min_height) " +
                        "VALUES ('ORDER_CREATED', 'Lyngby Adresse', 2800, 'ben@johannesfog.dk', 'alex@hotmail.com', 1, 380, 720, 180)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testValidGetCarportOrderById() throws NotFoundException, DatabaseException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertEquals(1, carportOrder.getId());
        assertEquals("Lyngby Adresse", carportOrder.getAddress().getStreet());
        assertEquals(2800, carportOrder.getAddress().getZip().getZipCode());
        assertTrue(carportOrder.getEmployee().isPresent());
        assertEquals("ben@johannesfog.dk", carportOrder.getEmployee().get().getEmail());
        assertEquals("alex@hotmail.com", carportOrder.getCustomer().getEmail());
        assertEquals(1, carportOrder.getRoof().getId());
        assertEquals(380.0, carportOrder.getWidth());
        assertEquals(720.0, carportOrder.getLength());
        assertEquals(180.0, carportOrder.getMinHeight());
        assertEquals(Optional.empty(), carportOrder.getRemarks());
        assertEquals("ORDER_CREATED", carportOrder.getOrderStatus().getStatus());
        assertEquals(Optional.empty(), carportOrder.getToolRoom());
        assertEquals(Optional.empty(), carportOrder.getPrice());
    }

    @Test
    void testInvalidGetCarportOrderById() {
        assertThrows(NotFoundException.class, () -> CarportOrderFacade.getCarportOrderById(0, connectionPool));
    }

    @Test
    void testValidGetCarportOrdersByCustomer() throws NotFoundException, DatabaseException, ValidationException {
        Customer customer = CustomerFacade.getCustomerByEmail("alex@hotmail.com", connectionPool);
        List<CarportOrder> carportOrders = CarportOrderFacade.getCarportOrdersByCustomer(customer, connectionPool);
        assertEquals(1, carportOrders.size());
        assertEquals(1, carportOrders.get(0).getId());
    }

    @Test
    void testValidGetCarportOrdersByCustomerEmptyList() throws NotFoundException, DatabaseException, ValidationException {
        Customer customer = CustomerFacade.getCustomerByEmail("ben@gmail.com", connectionPool);
        List<CarportOrder> carportOrders = CarportOrderFacade.getCarportOrdersByCustomer(customer, connectionPool);
        assertEquals(0, carportOrders.size());
    }

    @Test
    void testInvalidGetCarportOrdersByCustomerNullCustomer() {
        assertThrows(ValidationException.class, () -> CarportOrderFacade.getCarportOrdersByCustomer(null, connectionPool).size());
    }

    @Test
    void testValidGetCarportOrdersByEmployee() throws NotFoundException, DatabaseException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeByEmail("ben@johannesfog.dk", connectionPool);
        List<CarportOrder> carportOrders = CarportOrderFacade.getCarportOrdersByEmployee(employee, connectionPool);
        assertEquals(1, carportOrders.size());
        assertEquals(1, carportOrders.get(0).getId());
    }

    @Test
    void testValidGetCarportOrdersByEmployeeEmptyList() throws NotFoundException, DatabaseException, ValidationException {
        Employee employee = EmployeeFacade.getEmployeeByEmail("allan@johannesfog.dk", connectionPool);
        List<CarportOrder> carportOrders = CarportOrderFacade.getCarportOrdersByEmployee(employee, connectionPool);
        assertEquals(0, carportOrders.size());
    }

    @Test
    void testInvalidGetCarportOrdersByEmployeeNullEmployee() {
        assertThrows(ValidationException.class, () -> CarportOrderFacade.getCarportOrdersByEmployee(null, connectionPool).size());
    }

    @Test
    void testValidCreateCarportOrder() throws NotFoundException, DatabaseException, ValidationException {
        Customer customer = CustomerFacade.getCustomerByEmail("ben@gmail.com", connectionPool);
        Zip zip = ZipFacade.getZipByZipCode(2730, connectionPool);
        Address address = new Address("Herlev Adresse", zip);
        Roof roof = RoofFacade.getRoofById(1, connectionPool);
        CarportOrder carportOrder = CarportOrderFacade.createCarportOrder(customer, address, 360, 720, 300, roof, Optional.empty(), Optional.empty(), connectionPool);
        assertEquals(2, carportOrder.getId());
        assertEquals("Herlev Adresse", carportOrder.getAddress().getStreet());
        assertEquals(2730, carportOrder.getAddress().getZip().getZipCode());
        assertEquals("ben@gmail.com", carportOrder.getCustomer().getEmail());
        assertEquals(1, carportOrder.getRoof().getId());
        assertEquals(360, carportOrder.getWidth());
        assertEquals(720, carportOrder.getLength());
        assertEquals(300, carportOrder.getMinHeight());
        assertEquals(Optional.empty(), carportOrder.getRemarks());
        assertEquals("ORDER_CREATED", carportOrder.getOrderStatus().getStatus());
        assertEquals(Optional.empty(), carportOrder.getToolRoom());
        assertEquals(Optional.empty(), carportOrder.getPrice());
        assertFalse(carportOrder.getEmployee().isPresent());
    }

    @Test
    void testInvalidCreateCarportOrderCustomerNull() throws NotFoundException, DatabaseException {
        Zip zip = ZipFacade.getZipByZipCode(2730, connectionPool);
        Address address = new Address("Herlev Adresse", zip);
        Roof roof = RoofFacade.getRoofById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.createCarportOrder(null, address, 360, 720, 300, roof, Optional.empty(), Optional.empty(), connectionPool));
    }

    @Test
    void testInvalidCreateCarportOrderAddressNull() throws NotFoundException, DatabaseException {
        Customer customer = CustomerFacade.getCustomerByEmail("ben@gmail.com", connectionPool);
        Roof roof = RoofFacade.getRoofById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.createCarportOrder(customer, null, 360, 720, 300, roof, Optional.empty(), Optional.empty(), connectionPool));
    }

    @Test
    void testInvalidCreateCarportOrderNegativeWidth() throws NotFoundException, DatabaseException {
        Customer customer = CustomerFacade.getCustomerByEmail("ben@gmail.com", connectionPool);
        Zip zip = ZipFacade.getZipByZipCode(2730, connectionPool);
        Address address = new Address("Herlev Adresse", zip);
        Roof roof = RoofFacade.getRoofById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.createCarportOrder(customer, address, -1, 720, 300, roof, Optional.empty(), Optional.empty(), connectionPool));
    }

    @Test
    void testInvalidCreateCarportOrderNegativeLength() throws NotFoundException, DatabaseException {
        Customer customer = CustomerFacade.getCustomerByEmail("ben@gmail.com", connectionPool);
        Zip zip = ZipFacade.getZipByZipCode(2730, connectionPool);
        Address address = new Address("Herlev Adresse", zip);
        Roof roof = RoofFacade.getRoofById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.createCarportOrder(customer, address, 360, -1, 300, roof, Optional.empty(), Optional.empty(), connectionPool));
    }

    @Test
    void testInvalidCreateCarportOrderNegativeMinHeight() throws NotFoundException, DatabaseException {
        Customer customer = CustomerFacade.getCustomerByEmail("ben@gmail.com", connectionPool);
        Zip zip = ZipFacade.getZipByZipCode(2730, connectionPool);
        Address address = new Address("Herlev Adresse", zip);
        Roof roof = RoofFacade.getRoofById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.createCarportOrder(customer, address, 360, 720, -1, roof, Optional.empty(), Optional.empty(), connectionPool));
    }

    @Test
    void testInvalidCreateCarportOrderRoofNull() throws NotFoundException, DatabaseException {
        Customer customer = CustomerFacade.getCustomerByEmail("ben@gmail.com", connectionPool);
        Zip zip = ZipFacade.getZipByZipCode(2730, connectionPool);
        Address address = new Address("Herlev Adresse", zip);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.createCarportOrder(customer, address, 360, 720, 300, null, Optional.empty(), Optional.empty(), connectionPool));
    }

    @Test
    void testValidUpdateToolRoom() throws NotFoundException, DatabaseException, ValidationException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertFalse(carportOrder.getToolRoom().isPresent());
        Optional<ToolRoom> toolRoom = Optional.of(new ToolRoom(100, 100));
        CarportOrderFacade.updateToolRoom(carportOrder, toolRoom, connectionPool);
        assertTrue(carportOrder.getToolRoom().isPresent());
        assertEquals(100, carportOrder.getToolRoom().get().getLength());
        assertEquals(100, carportOrder.getToolRoom().get().getWidth());
    }

    @Test
    void testValidUpdateToolRoomEmpty() throws NotFoundException, DatabaseException, ValidationException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertFalse(carportOrder.getToolRoom().isPresent());
        Optional<ToolRoom> toolRoom = Optional.of(new ToolRoom(100, 100));
        CarportOrderFacade.updateToolRoom(carportOrder, toolRoom, connectionPool);
        assertTrue(carportOrder.getToolRoom().isPresent());
        assertEquals(100, carportOrder.getToolRoom().get().getLength());
        assertEquals(100, carportOrder.getToolRoom().get().getWidth());
        CarportOrderFacade.updateToolRoom(carportOrder, Optional.empty(), connectionPool);
        assertFalse(carportOrder.getToolRoom().isPresent());
    }

    @Test
    void testInvalidUpdateToolRoomNullCarportOrder() {
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateToolRoom(null, Optional.of(new ToolRoom(100, 100)), connectionPool));
    }

    @Test
    void testValidUpdateAddress() throws NotFoundException, DatabaseException, ValidationException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertEquals("Lyngby Adresse", carportOrder.getAddress().getStreet());
        assertEquals(2800, carportOrder.getAddress().getZip().getZipCode());
        assertEquals("Lyngby", carportOrder.getAddress().getZip().getCityName());
        Address address = new Address("Updated Adresse", new Zip(2730, "Herlev"));
        CarportOrderFacade.updateAddress(carportOrder, address, connectionPool);
        assertEquals("Updated Adresse", carportOrder.getAddress().getStreet());
        assertEquals(2730, carportOrder.getAddress().getZip().getZipCode());
        assertEquals("Herlev", carportOrder.getAddress().getZip().getCityName());
    }

    @Test
    void testInvalidUpdateAddressNullAddress() throws NotFoundException, DatabaseException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateAddress(carportOrder, null, connectionPool));
    }

    @Test
    void testInvalidUpdateAddressNullCarportOrder() {
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateAddress(null, new Address("Updated Adresse", new Zip(2800, "Lyngby")), connectionPool));
    }

    @Test
    void testValidUpdateLength() throws NotFoundException, DatabaseException, ValidationException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertEquals(720, carportOrder.getLength());
        CarportOrderFacade.updateLength(carportOrder, 820, connectionPool);
        assertEquals(820, carportOrder.getLength());
    }

    @Test
    void testInvalidUpdateLengthNullCarportOrder() {
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateLength(null, 400, connectionPool));
    }

    @Test
    void testInvalidUpdateLengthNegativeLength() throws NotFoundException, DatabaseException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateLength(carportOrder, -1, connectionPool));
    }

    @Test
    void testValidUpdateWidth() throws NotFoundException, DatabaseException, ValidationException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertEquals(380, carportOrder.getWidth());
        CarportOrderFacade.updateWidth(carportOrder, 480, connectionPool);
        assertEquals(480, carportOrder.getWidth());
    }

    @Test
    void testInvalidUpdateWidthNullCarportOrder() {
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateWidth(null, 600, connectionPool));
    }

    @Test
    void testInvalidUpdateWidthNegativeWidth() throws NotFoundException, DatabaseException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateWidth(carportOrder, -1, connectionPool));
    }

    @Test
    void testValidUpdateMinHeight() throws NotFoundException, DatabaseException, ValidationException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertEquals(180, carportOrder.getMinHeight());
        CarportOrderFacade.updateMinHeight(carportOrder, 240, connectionPool);
        assertEquals(240, carportOrder.getMinHeight());
    }

    @Test
    void testInvalidUpdateMinHeightNullCarportOrder() {
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateMinHeight(null, 300, connectionPool));
    }

    @Test
    void testInvalidUpdateMinHeightNegativeMinHeight() throws NotFoundException, DatabaseException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updateMinHeight(carportOrder, -1, connectionPool));
    }

    @Test
    void testValidUpdatePrice() throws NotFoundException, DatabaseException, ValidationException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertFalse(carportOrder.getPrice().isPresent());
        CarportOrderFacade.updatePrice(carportOrder, Optional.of(1000F), connectionPool);
        assertTrue(carportOrder.getPrice().isPresent());
        assertEquals(1000, carportOrder.getPrice().get());
    }

    @Test
    void testValidUpdatePriceEmpty() throws NotFoundException, DatabaseException, ValidationException {
        CarportOrder carportOrder = CarportOrderFacade.getCarportOrderById(1, connectionPool);
        assertFalse(carportOrder.getPrice().isPresent());
        CarportOrderFacade.updatePrice(carportOrder, Optional.of(1000F), connectionPool);
        assertTrue(carportOrder.getPrice().isPresent());
        assertEquals(1000, carportOrder.getPrice().get());
        CarportOrderFacade.updatePrice(carportOrder, Optional.empty(), connectionPool);
        assertFalse(carportOrder.getPrice().isPresent());
    }

    @Test
    void testInvalidUpdatePriceNullCarportOrder() {
        assertThrows(ValidationException.class, () -> CarportOrderFacade.updatePrice(null, Optional.of(1000F), connectionPool));
    }
}