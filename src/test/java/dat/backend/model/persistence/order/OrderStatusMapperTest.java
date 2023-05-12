package dat.backend.model.persistence.order;

import dat.backend.model.entities.order.OrderStatus;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.TestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusMapperTest extends TestDatabase {

    @BeforeEach
    public void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM orderstatus");

                // Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO orderstatus (status, sortvalue, displayname) " +
                        "VALUES ('ORDER_CREATED', 1, 'Ordre oprettet'), ('ORDER_ACCEPTED', 2, 'Ordre accepteret'), ('ORDER_COMPLETED', 3, 'Ordre afsluttet'), ('ORDER_CANCELLED', 4, 'Ordre annulleret')");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testValidGetOrderStatusByStatus() throws NotFoundException, DatabaseException {
        OrderStatus orderStatus = OrderStatusFacade.getOrderStatusByStatus("ORDER_CREATED", connectionPool);
        assertEquals("ORDER_CREATED", orderStatus.getStatus());
        assertEquals(1, orderStatus.getSortValue());
        assertEquals("Ordre oprettet", orderStatus.getDisplayName());
    }

    @Test
    void testInvalidGetOrderStatusByStatus() throws DatabaseException {
        assertThrows(NotFoundException.class, () -> OrderStatusFacade.getOrderStatusByStatus("ORDERSTATUS_SHOULD_NOT_FOUND", connectionPool));
    }
}