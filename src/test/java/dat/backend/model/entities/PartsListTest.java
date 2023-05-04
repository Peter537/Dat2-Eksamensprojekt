package dat.backend.model.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartsListTest {

    @Test
    void calculateNumberOfPolesWidth0() {
        // Arrange
        int expected = 0;

        // Act
        int actual = PartsList.calculateNumberOfPolesWidth(670);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void calculateNumberOfPolesWidth1() {
        // Arrange
        int expected = 1;

        // Act
        int actual = PartsList.calculateNumberOfPolesWidth(671);

        // Assert
        assertEquals(expected, actual);
    }


    @Test
    void calculateNumberOfPolesLength0() {
        int expected = 0;
        int actual = PartsList.calculateNumberOfPolesLength(480);
        assertEquals(expected, actual);
    }

    @Test
    void calculateNumberOfPolesLength1() {
        int expected = 1;
        int actual = PartsList.calculateNumberOfPolesLength(481);
        assertEquals(expected, actual);
    }

    @Test
    void calculateNumberOfPoles4() {
        int expected = 4;
        int actual = PartsList.calculateNumberOfPoles(480, 670);
        int polesBetweenLength = PartsList.calculateNumberOfPolesLength(480);
        int polesBetweenWidth = PartsList.calculateNumberOfPolesWidth(670);

        assertEquals(0, polesBetweenLength);
        assertEquals(0, polesBetweenWidth);
        assertEquals(expected, actual);
    }

    @Test
    void calculateNumberOfPoles6(){
        int expected = 6;
        int actual = PartsList.calculateNumberOfPoles(481, 670);
        assertEquals(expected, actual);
    }

    @Test
    void calculateNumberOfPoles9(){
        int expected = 9;
        int actual = PartsList.calculateNumberOfPoles(481, 671);
        assertEquals(expected, actual);
    }



    @Test
    void calculateNumberOfPlates() {
        int expected = 3;
        int actual = PartsList.calculateNumberOfPlates(671);
        assertEquals(expected, actual);
    }

    @Test
    void calculateNumberOfRafters10() {
        int expected = 10;
        int actual = PartsList.calculateNumberOfRafters(600);
        assertEquals(expected, actual);
    }

    @Test
    void calculateNumberOfRafters11() {
        int expected = 10;
        int actual = PartsList.calculateNumberOfRafters(601);
        assertEquals(expected, actual);
    }
    @Test
    void calculateSpanBetweenPlates0() {
        double expected = 600;
        double actual = PartsList.calculateSpanBetweenPlates(670);
        assertEquals(expected, actual);
    }

    @Test
    void calculateSpanBetweenPlates1() {
        double expected = 301;
        double actual = PartsList.calculateSpanBetweenPlates(672);
        assertEquals(expected, actual);
    }

    @Test
    void calculateDimensions0() {
        double expected = 295;
        double actual = PartsList.calculateDimensions(670);
        assertEquals(expected, actual);
    }

    @Test
    void calculateDimensions1() {
        double expected = 170;
        double actual = PartsList.calculateDimensions(672);
        assertEquals(expected, actual);
    }


}