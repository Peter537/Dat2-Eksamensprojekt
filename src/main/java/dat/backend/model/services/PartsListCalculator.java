package dat.backend.model.services;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.LumberFacade;
import dat.backend.model.persistence.item.LumberTypeFacade;

import java.util.Collections;
import java.util.List;

public class PartsListCalculator {

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
        int minlength = calculateLengthOfLumber(length, rafterType, connectionPool);
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
        int minlength = calculateLengthOfLumber(width, rafterType, connectionPool);
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

        return (int) (Math.ceil(widthBetweenPoles / 600.0) - 1);
    }

    public static int calculateNumberOfPolesLength(int length) {
        int lengthBetweenPoles = length - 140;
        if (lengthBetweenPoles < 20) {
            throw new IllegalArgumentException("Length of carport is too small.");
        }

        return (int) (Math.ceil(lengthBetweenPoles / 340.0) - 1);
    }

    public static int calculateNumberOfPoles(int length, int width) {
        int polesBetweenLength = calculateNumberOfPolesLength(length);
        int polesBetweenWidth = calculateNumberOfPolesWidth(width);
        return (2 + polesBetweenWidth) * (2 + polesBetweenLength);
    }

    public static int calculateNumberOfPlates(int width) {
        return 2 + calculateNumberOfPolesWidth(width);
    }

    public static int calculateNumberOfRafters(int length) {
        return (int) (Math.ceil(length / 60.0));
    }

    public static double calculateSpanBetweenPlates(int width) {
        int widthBetweenPoles = width - 70;
        return widthBetweenPoles / (calculateNumberOfPlates(width) - 1.0);
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

    /*
    max length of rafter and plate lumber is 720 cm. If the length is longer than this, we will need two pieces of lumber.
    The length of the rafter is width of carport. The length of the plate is length of carport.
    Assume that the lumber is divided into two(or more) pieces of equal length.
    */
    public static int calculateNumber(int length, LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        List<Lumber> listRafter = LumberFacade.getLumberByType(lumberType, connectionPool);
        Collections.sort(listRafter);
        Collections.reverse(listRafter);
        int maxLumberLength = listRafter.get(0).getLength();
        double number = (double) length / (double) maxLumberLength;

        return (int) Math.ceil(number);
    }

    public static int calculateLengthOfLumber(int length, LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        return length / calculateNumber(length, lumberType, connectionPool);
    }
}