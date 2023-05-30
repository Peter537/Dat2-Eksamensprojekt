package dat.backend.model.entities.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    @Test
    void testValidGetAddress() {
        Zip zip = new Zip(1234, "City");
        String street = "Street";
        Address address = new Address(street, zip);
        String expected = street + ", " + zip.getZipCode() + " " + zip.getCityName();
        String actual = address.getAddress();
        assertEquals(expected, actual);
    }
}