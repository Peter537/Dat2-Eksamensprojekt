package dat.backend.model.entities;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.LumberFacade;
import dat.backend.model.persistence.item.LumberTypeFacade;

import java.util.Collections;
import java.util.List;

public class PartsList {

    private Lumber pole; // stolpe
    private Lumber plate; // rem
    private Lumber rafter; // spær

    private int numberOfPoles;
    private int numberOfPlates;
    private int numberOfRafters;

    private int totalPrice;

    public PartsList(int height, int length, int width, ConnectionPool connectionPool) throws DatabaseException {
        this.pole = calculatePole(height, width, connectionPool);
        this.plate = calculatePlate(width, connectionPool);
        this.rafter = calculateRafter(length, width, connectionPool);
        this.numberOfPoles = calculateNumberOfPoles(length, width);
        this.numberOfPlates = calculateNumberOfPlates(width, length);
        this.numberOfRafters = calculateNumberOfRafters(length, width);
        this.totalPrice = calculateTotalPrice();
    }

    public int calculateTotalPrice() {
        return (pole.getPrice() * numberOfPoles) + (plate.getPrice() * numberOfPlates) + (rafter.getPrice() * numberOfRafters); // the getPrice() method is inherited from Item. It is not implemented yet.
    }

    public static Lumber calculatePole(int height, int width, ConnectionPool connectionPool) throws DatabaseException {
        LumberType pole = LumberTypeFacade.getLumberTypeByType("POLE", connectionPool).get(0);
        List<Lumber> listPole = LumberFacade.getLumberByType(pole, connectionPool);
        Collections.sort(listPole);
        LumberType rafterType = calculateRafterType(width, connectionPool);
        int minHeight = height + 90 + ((int) rafterType.getWidth() / 10);
        for (Lumber lumber : listPole) {
            if (lumber.getLength() >= minHeight) {
                return lumber;
            }
        }

        throw new IllegalArgumentException("No pole found with the required length.");
    }

    public static LumberType calculateRafterType(int width, ConnectionPool connectionPool) throws DatabaseException {
        List<LumberType> listRafter = LumberTypeFacade.getLumberTypeByType("RAFTER", connectionPool);
        Collections.sort(listRafter);
        float dim = (float) calculateDimensions(width);
        for (LumberType lumberType : listRafter) {
            if (lumberType.getWidth() >= dim) {
                return lumberType;
            }
        }

        throw new IllegalArgumentException("No rafter found with the required width.");
    }

    public static Lumber calculateRafter(int length, int width, ConnectionPool connectionPool) throws DatabaseException {
        LumberType rafterType = calculateRafterType(width, connectionPool);
        List<Lumber> listRafter = LumberFacade.getLumberByType(rafterType, connectionPool);
        Collections.sort(listRafter);
        int minlength = calculateLengthOfLumber(length);
        for (Lumber lumber : listRafter) {
            if (lumber.getLength() >= minlength) {
                return lumber;
            }
        }

        throw new IllegalArgumentException("No rafter found with the required length.");
    }

    public static Lumber calculatePlate(int width, ConnectionPool connectionPool) throws DatabaseException {
        LumberType rafterType = calculateRafterType(width, connectionPool);
        List<Lumber> listRafter = LumberFacade.getLumberByType(rafterType, connectionPool);
        Collections.sort(listRafter);
        int minlength = calculateLengthOfLumber(width);
        for (Lumber lumber : listRafter) {
            if (lumber.getLength() >= minlength) {
                return lumber;
            }
        }

        throw new IllegalArgumentException("No plates found with the required length.");
    }

    public static int calculateNumberOfPolesWidth(int width) {
        int widthBetweenPoles = width - 70;
        if (widthBetweenPoles < 100) {
            throw new IllegalArgumentException("Width of carport is too small.");
        }

        int numberOfPolesWidth = (int) (Math.ceil(widthBetweenPoles / 600.0) - 1);
        return numberOfPolesWidth;
    }

    public static int calculateNumberOfPolesLength(int length) {
        int lengthBetweenPoles = length - 140;
        if (lengthBetweenPoles < 20) {
            throw new IllegalArgumentException("Length of carport is too small.");
        }

        int numberOfPolesLength = (int) (Math.ceil(lengthBetweenPoles / 340.0) - 1);
        return numberOfPolesLength;
    }

    public static int calculateNumberOfPoles(int length, int width) {
        int polesBetweenLength = calculateNumberOfPolesLength(length);
        int polesBetweenWidth = calculateNumberOfPolesWidth(width);
        int numberOfPoles = (2 + polesBetweenWidth) * (2 + polesBetweenLength);
        return numberOfPoles;
    }

    public static int calculateNumberOfPlates(int width, int length) {
        int numberOfPlates = 2 + calculateNumberOfPolesWidth(width) * ((int) Math.ceil(length / 720.0));
        return numberOfPlates;
    }

    public static int calculateNumberOfPlates1D(int width) { // to calculate span between plates
        int numberOfPlates = 2 + calculateNumberOfPolesWidth(width);
        return numberOfPlates;
    }

    public static int calculateNumberOfRafters(int length, int width) {
        int numberOfRafters = (int) (Math.ceil(length / 60)) * ((int) Math.ceil(width / 720.0));
        return numberOfRafters;
    }

    public static double calculateSpanBetweenPlates(int width) {
        int widthBetweenPoles = width - 70;
        double span = widthBetweenPoles / (calculateNumberOfPlates1D(width) - 1.0);
        return span;
    }

    static double[][] spanTable = {
            {120, 145, 170, 195, 220, 245, 295},// dimensions in mm
            {248, 300, 351, 402, 452, 502, 600},// max. span in cm
    };

    public static double calculateDimensions(int width) {
        double span = calculateSpanBetweenPlates(width);
        double dimensions;
        for (int i = 0; i < spanTable[0].length; i++) {
            if (spanTable[1][i] >= span) {
                dimensions = spanTable[0][i];
                return dimensions;
            }
        }

        throw new IllegalArgumentException("No dimensions found with the required span.");
    }

    /* max length of rafter and plate lumber is 720 cm. If the length is longer than this, we will need two pieces of lumber.
    The length of the rafter is width of carport. The length of the plate is length of carport.
    Assume that the lumber is divided into two(or more) pieces of equal length.
    */
    public static int calculateLengthOfLumber(int length) {
        int minlength = length / ((int) Math.ceil(length / 720.0));
        return minlength;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Lumber getPole() {
        return this.pole;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPole(Lumber pole) {
        this.pole = pole;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Lumber getPlate() {
        return this.plate;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setPlate(Lumber plate) {
        this.plate = plate;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Lumber getRafter() {
        return this.rafter;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setRafter(Lumber rafter) {
        this.rafter = rafter;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getNumberOfPoles() {
        return this.numberOfPoles;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setNumberOfPoles(int numberOfPoles) {
        this.numberOfPoles = numberOfPoles;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getNumberOfPlates() {
        return this.numberOfPlates;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setNumberOfPlates(int numberOfPlates) {
        this.numberOfPlates = numberOfPlates;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getNumberOfRafters() {
        return this.numberOfRafters;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setNumberOfRafters(int numberOfRafters) {
        this.numberOfRafters = numberOfRafters;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getTotalPrice() {
        return this.totalPrice;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}