package dat.backend.model.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartsListCalculatorTest {

    @Test
    void testValidCalculateNumberOfPolesWidth0() {
        // Arrange
        int expected = 0;

        // Act
        int actual = PartsListCalculator.calculateNumberOfPolesWidth(670);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateNumberOfPolesWidth1() {
        // Arrange
        int expected = 1;

        // Act
        int actual = PartsListCalculator.calculateNumberOfPolesWidth(671);

        // Assert
        assertEquals(expected, actual);
    }


    @Test
    void testValidCalculateNumberOfPolesLength0() {
        int expected = 0;
        int actual = PartsListCalculator.calculateNumberOfPolesLength(480);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateNumberOfPolesLength1() {
        int expected = 1;
        int actual = PartsListCalculator.calculateNumberOfPolesLength(481);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateNumberOfPoles4() {
        int expected = 4;
        int actual = PartsListCalculator.calculateNumberOfPoles(480, 670);
        int polesBetweenLength = PartsListCalculator.calculateNumberOfPolesLength(480);
        int polesBetweenWidth = PartsListCalculator.calculateNumberOfPolesWidth(670);

        assertEquals(0, polesBetweenLength);
        assertEquals(0, polesBetweenWidth);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateNumberOfPoles6() {
        int expected = 6;
        int actual = PartsListCalculator.calculateNumberOfPoles(481, 670);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateNumberOfPoles9() {
        int expected = 9;
        int actual = PartsListCalculator.calculateNumberOfPoles(481, 671);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateNumberOfPlates() {
        int expected = 3;
        int actual = PartsListCalculator.calculateNumberOfPlates(671);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateNumberOfRafters10() {
        int expected = 10;
        int actual = PartsListCalculator.calculateNumberOfRafters(600);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateNumberOfRafters11() {
        int expected = 11;
        int actual = PartsListCalculator.calculateNumberOfRafters(601);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateSpanBetweenPlates0() {
        double expected = 600;
        double actual = PartsListCalculator.calculateSpanBetweenPlates(670);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateSpanBetweenPlates1() {
        double expected = 301;
        double actual = PartsListCalculator.calculateSpanBetweenPlates(672);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateDimensions0() {
        double expected = 295;
        double actual = PartsListCalculator.calculateDimensions(670);
        assertEquals(expected, actual);
    }

    @Test
    void testValidCalculateDimensions1() {
        double expected = 170;
        double actual = PartsListCalculator.calculateDimensions(672);
        assertEquals(expected, actual);
    }
}