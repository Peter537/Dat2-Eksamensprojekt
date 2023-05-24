package dat.backend.model.services;

import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.LumberFacade;
import dat.backend.model.persistence.item.LumberTypeFacade;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PartsListCalculator {

    /**
     *
     */

    /**
     * Calculate pole
     *
     * @param height         The height of the carport
     * @param width          The width of the carport
     * @param connectionPool The connection pool to use
     * @return The pole
     * @throws DatabaseException        if an error occurs while communicating with the database
     * @throws IllegalArgumentException if no pole is found with the required length(if your carport is too tall)
     */
    public static Lumber calculatePole(int height, int width, ConnectionPool connectionPool) throws DatabaseException {
        LumberType pole = LumberTypeFacade.getLumberTypeByType("POLE", connectionPool).get(0);
        List<Lumber> listPole = LumberFacade.getLumberByType(pole, connectionPool);
        Collections.sort(listPole);
        LumberType rafterType = calculateRafterType(width, connectionPool);
        int minHeight = height + 90 + ((int) rafterType.getWidth() / 10);
        for (Lumber lumber : listPole) {
            if (lumber.getLength() >= minHeight) {
                lumber.setDescription(Optional.of("Dette er en stolpe"));
                return lumber;
            }
        }

        throw new IllegalArgumentException("No pole found with the required length.");
    }

    /**
     * Calculate rafter type based on width
     *
     * @param width          The width of the carport
     * @param connectionPool The connection pool to use
     * @return The rafter type
     * @throws DatabaseException        if an error occurs while communicating with the database
     * @throws IllegalArgumentException if no rafter is found with the required width
     */
    static LumberType calculateRafterType(int width, ConnectionPool connectionPool) throws DatabaseException {
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

    /**
     * Calculate the rafter
     *
     * @param length         The length of the carport
     * @param width          The width of the carport
     * @param connectionPool The connection pool to use
     * @return The rafter
     * @throws DatabaseException        if an error occurs while communicating with the database
     * @throws IllegalArgumentException if no rafter is found with the required length
     */
    public static Lumber calculateRafter(int length, int width, ConnectionPool connectionPool) throws DatabaseException {
        LumberType rafterType = calculateRafterType(width, connectionPool);
        List<Lumber> listRafter = LumberFacade.getLumberByType(rafterType, connectionPool);
        Collections.sort(listRafter);
        int minLength = calculateLengthOfLumber(length, rafterType, connectionPool);
        for (Lumber lumber : listRafter) {
            if (lumber.getLength() >= minLength) {
                lumber.setDescription(Optional.of("Dette er en spær"));
                return lumber;
            }
        }

        throw new IllegalArgumentException("No rafter found with the required length.");
    }

    /**
     * Calculate the plate
     *
     * @param width          The width of the carport
     * @param connectionPool The connection pool to use
     * @return The plate
     * @throws DatabaseException        if an error occurs while communicating with the database
     * @throws IllegalArgumentException if no plate is found with the required length
     */
    public static Lumber calculatePlate(int width, ConnectionPool connectionPool) throws DatabaseException {
        LumberType rafterType = calculateRafterType(width, connectionPool);
        List<Lumber> listRafter = LumberFacade.getLumberByType(rafterType, connectionPool);
        Collections.sort(listRafter);
        int minLength = calculateLengthOfLumber(width, rafterType, connectionPool);
        for (Lumber lumber : listRafter) {
            if (lumber.getLength() >= minLength) {
                lumber.setDescription(Optional.of("Dette er en rem"));
                return lumber;
            }
        }

        throw new IllegalArgumentException("No plates found with the required length.");
    }

    /**
     * Calculate the number of poles
     *
     * @param width The width of the carport
     * @return The number of poles
     */
    static int calculateNumberOfPolesWidth(int width) {
        int widthBetweenPoles = width - 70;
        if (widthBetweenPoles < 100) {
            throw new IllegalArgumentException("Width of carport is too small.");
        }

        return (int) (Math.ceil(widthBetweenPoles / 600.0) - 1);
    }

    /**
     * Calculate the number of poles on a length
     *
     * @param length The length of the carport
     * @return The number of poles
     */
    static int calculateNumberOfPolesLength(int length) {
        int lengthBetweenPoles = length - 140;
        if (lengthBetweenPoles < 20) {
            throw new IllegalArgumentException("Length of carport is too small.");
        }

        return (int) (Math.ceil(lengthBetweenPoles / 340.0) - 1);
    }

    /**
     * Calculate the number of poles
     *
     * @param length The length of the carport
     * @param width  The width of the carport
     * @return The number of poles
     */
    public static int calculateNumberOfPoles(int length, int width) {
        int polesBetweenLength = calculateNumberOfPolesLength(length);
        int polesBetweenWidth = calculateNumberOfPolesWidth(width);
        return (2 + polesBetweenWidth) * (2 + polesBetweenLength);
    }

    /**
     * Calculate the number of plates
     *
     * @param width The width of the carport
     * @return The number of plates
     */
    public static int calculateNumberOfPlates(int width) {
        return 2 + calculateNumberOfPolesWidth(width);
    }

    /**
     * Calculate the number of rafters
     *
     * @param length The length of the carport
     * @return The number of rafters
     */
    public static int calculateNumberOfRafters(int length) {
        return (int) (Math.ceil(length / 60.0) + 1);
    }

    /**
     * Calculate the amount of place needed between plates
     *
     * @param width The width of the carport
     * @return The amount of place needed between plates
     */
    static double calculateSpanBetweenPlates(int width) {
        int widthBetweenPoles = width - 70;
        return widthBetweenPoles / (calculateNumberOfPlates(width) - 1.0);
    }

    /**
     * This is a standard table for the dimentions of the rafter depending on the span between the plates, it is assumed that the span between the rafters is max. 60 cm. The table is taken from https://www.palsgaardspaer.dk/teknik/dimensionering/spaendviddetabeller-plankespaer/.
     */
    private static final double[][] spanTable = {
            {120, 145, 170, 195, 220, 245, 295},// dimensions in mm
            {248, 300, 351, 402, 452, 502, 600},// max. span in cm
    };

    /**
     * Calculate the width of the rafters
     *
     * @param width The width of the carport
     * @return The width of the rafter in mm
     */
    static double calculateDimensions(int width) {
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

    /**
     * max length of rafter and plate lumber is found in the database. If the length is longer than this, we will need two pieces of lumber.
     * The length of the rafter is width of carport. The length of the plate is length of carport.
     * Assume that the lumber is divided into two(or more) pieces of equal length.
     *
     * @param length         The length of the carport
     * @param lumberType     The type of lumber
     * @param connectionPool The connection pool to use
     * @return The number of pieces of lumber needed to cover the length
     * @throws DatabaseException        if an error occurs while communicating with the database
     * @throws IllegalArgumentException if no lumber is found with the required length
     */
    public static int calculateNumber(int length, LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        List<Lumber> listRafter = LumberFacade.getLumberByType(lumberType, connectionPool);
        Collections.sort(listRafter);
        Collections.reverse(listRafter);
        int maxLumberLength = listRafter.get(0).getLength();
        double number = (double) length / (double) maxLumberLength;
        return (int) Math.ceil(number);
    }

    /**
     * Calculate the length of the lumber
     *
     * @param length         The length of the carport
     * @param lumberType     The type of lumber
     * @param connectionPool The connection pool to use
     * @return The length of the lumber
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    public static int calculateLengthOfLumber(int length, LumberType lumberType, ConnectionPool connectionPool) throws DatabaseException {
        return length / calculateNumber(length, lumberType, connectionPool);
    }
}