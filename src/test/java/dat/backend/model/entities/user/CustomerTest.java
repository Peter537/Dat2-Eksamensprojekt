package dat.backend.model.entities.user;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testValidSetAddress() {
        Customer customer = new Customer(0, "email", "name", Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        for (int i = 1; i <= 3; i++) {
            assertFalse(customer.getAddress(i).isPresent());
            customer.setAddress(i, Optional.of(new Address("street", new Zip(2800, "city"))));
            assertTrue(customer.getAddress(i).isPresent());
            assertEquals("street", customer.getAddress(i).get().getStreet());
            assertEquals(2800, customer.getAddress(i).get().getZip().getZipCode());
            assertEquals("city", customer.getAddress(i).get().getZip().getCityName());
        }
    }

    @Test
    void testInvalidSetAddress() {
        Customer customer = new Customer(0, "email", "name", Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> customer.setAddress(0, Optional.of(new Address("street", new Zip(2800, "city")))));
        assertThrows(IllegalArgumentException.class, () -> customer.setAddress(4, Optional.of(new Address("street", new Zip(2800, "city")))));
    }

    @Test
    void testValidGetAddress() {
        Customer customer = new Customer(0, "email", "name", Optional.empty(), Optional.of(new Address("street1", new Zip(2800, "city1"))), Optional.of(new Address("street2", new Zip(2800, "city2"))), Optional.of(new Address("street3", new Zip(2800, "city3"))));
        for (int i = 1; i <= 3; i++) {
            assertTrue(customer.getAddress(i).isPresent());
            assertEquals("street" + i, customer.getAddress(i).get().getStreet());
            assertEquals(2800, customer.getAddress(i).get().getZip().getZipCode());
            assertEquals("city" + i, customer.getAddress(i).get().getZip().getCityName());
        }
    }

    @Test
    void testInvalidGetAddress() {
        Customer customer = new Customer(0, "email", "name", Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> customer.getAddress(0));
        assertThrows(IllegalArgumentException.class, () -> customer.getAddress(4));
    }
}