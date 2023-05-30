package dat.backend.model.services;

import dat.backend.model.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class ValidationTest {

    @Test
    void testValidValidateCustomer() {
        try {
            Validation.validateCustomer("Allan", "allan@gmail.com", "12345");
        } catch (ValidationException e) {
            fail("ValidationException not supposed to be thrown");
        }
    }

    @Test
    void testInvalidValidateCustomerNameNull() {
        assertThrows(ValidationException.class, () -> Validation.validateCustomer(null, "allan@gmail.com", "12345"));
    }

    @Test
    void testInvalidValidateCustomerEmailNull() {
        assertThrows(ValidationException.class, () -> Validation.validateCustomer("Allan", null, "12345"));
    }

    @Test
    void testInvalidValidateCustomerEmailJohannesFog() {
        assertThrows(ValidationException.class, () -> Validation.validateCustomer("Allan", "allan@johannesfog.dk", "12345"));
    }

    @Test
    void testInvalidValidateCustomerEmailNotValid() {
        assertThrows(ValidationException.class, () -> Validation.validateCustomer("Allan", "allan", "12345"));
    }

    @Test
    void testInvalidValidateCustomerPasswordNull() {
        assertThrows(ValidationException.class, () -> Validation.validateCustomer("Allan", "allan@gmail.com", null));
    }

    @Test
    void testInvalidValidateCustomerPasswordTooShort() {
        assertThrows(ValidationException.class, () -> Validation.validateCustomer("Allan", "allan@gmail.com", "12"));
    }

    @Test
    void testInvalidValidateCustomerPasswordTooLong() {
        assertThrows(ValidationException.class, () -> Validation.validateCustomer("Allan", "allan@gmail.com", "0123456789012345678901234567890123456789012345678901234567890123456789"));
    }

    @Test
    void testValidValidateEmployee() {
        try {
            Validation.validateEmployee("Allan", "allan@johannesfog.dk", "12345");
        } catch (ValidationException e) {
            fail("ValidationException not supposed to be thrown");
        }
    }

    @Test
    void testInvalidValidateEmployeeNameNull() {
        assertThrows(ValidationException.class, () -> Validation.validateEmployee(null, "allan@johannesfog.dk", "12345"));
    }

    @Test
    void testInvalidValidateEmployeeEmailNull() {
        assertThrows(ValidationException.class, () -> Validation.validateEmployee("Allan", null, "12345"));
    }

    @Test
    void testInvalidValidateEmployeeEmailNotJohannesFog() {
        assertThrows(ValidationException.class, () -> Validation.validateEmployee("Allan", "allan@gmail.com", "12345"));
    }

    @Test
    void testInvalidValidateEmployeeEmailNotValid() {
        assertThrows(ValidationException.class, () -> Validation.validateEmployee("Allan", "allan", "12345"));
    }

    @Test
    void testInvalidValidateEmployeePasswordNull() {
        assertThrows(ValidationException.class, () -> Validation.validateEmployee("Allan", "allan@johannesfog.dk", null));
    }

    @Test
    void testInvalidValidateEmployeePasswordTooShort() {
        assertThrows(ValidationException.class, () -> Validation.validateEmployee("Allan", "allan@johannesfog.dk", "12"));
    }

    @Test
    void testInvalidValidateEmployeePasswordTooLong() {
        assertThrows(ValidationException.class, () -> Validation.validateEmployee("Allan", "allan@johannesfog.dk", "0123456789012345678901234567890123456789012345678901234567890123456789"));
    }
}