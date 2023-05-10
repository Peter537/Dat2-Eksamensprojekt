package dat.backend.model.entities;

import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.LumberFacade;
import dat.backend.model.persistence.item.LumbertypeFacade;

import java.util.ArrayList;
import java.util.Collections;

import static dat.backend.model.persistence.item.LumbertypeFacade.getLumbertypeByType;

public class PartsList {

    private Lumber pole; // stolpe
    private Lumber plate; // rem
    private Lumber rafter; // spær

    private int numberOfPoles;
    private int numberOfPlates;
    private int numberOfRafters;

    private int totalPrice;
;




    //TODO: Given height, length and width. Calculate the number of poles, plates and rafters needed.


    public PartsList(int height, int length, int width) throws DatabaseException {
        this.pole = calculatePole(height);
        this.plate = calculatePlate(width);
        this.rafter = calculateRafter(length, width);
        this.numberOfPoles = calculateNumberOfPoles(length, width);
        this.numberOfPlates = calculateNumberOfPlates(width);
        this.numberOfRafters = calculateNumberOfRafters(length);


        this.totalPrice = calculateTotalPrice();

  }

    private int calculateTotalPrice() {
        return (pole.getPrice() * numberOfPoles) + (plate.getPrice() * numberOfPlates) + (rafter.getPrice() * numberOfRafters); // the getPrice() method is inherited from Item. It is not implemented yet.
    }


    private ConnectionPool connectionPool;


    public Lumber calculatePole(int height) throws DatabaseException {
        LumberType pole = getLumbertypeByType("POLE", connectionPool).get().get(0);
        ArrayList<Lumber> lpole = LumberFacade.getLumberByType(pole, connectionPool).get();
        Collections.sort(lpole);

        int minheight = height + 90 + (int)plate.getLumberType().getWidth();

        for (Lumber lumber : lpole) {
            if (lumber.getLength() >= minheight) {
                return lumber;
            }
        }
        throw new IllegalArgumentException("No pole found with the required length.");
    }


    public LumberType calculateRafterType(int width) throws DatabaseException {
        ArrayList<LumberType> lrafter = LumbertypeFacade.getLumbertypeByType("RAFTER", connectionPool).get();
        Collections.sort(lrafter);
        float dim = (float)calculateDimensions(width);
        for (LumberType lumberType : lrafter) {
            if (lumberType.getWidth() >= dim) {
                return lumberType;
                }
            }
        throw new IllegalArgumentException("No rafter found with the required width.");
    }

    public Lumber calculateRafter(int length, int width) throws DatabaseException {
        LumberType rafterType = calculateRafterType(width);
        ArrayList<Lumber> lrafter = LumberFacade.getLumberByType(rafterType, connectionPool).get();
        Collections.sort(lrafter);
        int minlength = calculateLengthOfLumber(length);
        for (Lumber lumber : lrafter) {
            if (lumber.getLength() >= minlength) {
                return lumber;
            }
        }
        throw new IllegalArgumentException("No rafter found with the required length.");
    }

    public Lumber calculatePlate(int width) throws DatabaseException {
        LumberType rafterType = calculateRafterType(width);
        ArrayList<Lumber> lrafter = LumberFacade.getLumberByType(rafterType, connectionPool).get();
        Collections.sort(lrafter);
        int minlength = calculateLengthOfLumber(width);
        for (Lumber lumber : lrafter) {
            if (lumber.getLength() >= minlength) {
                return lumber;
            }
        }
        throw new IllegalArgumentException("No plates found with the required length.");

    }

    public static int calculateNumberOfPolesWidth(int width) {
        int widthBetweenPoles = width - 70;
        if (widthBetweenPoles < 100) {throw new IllegalArgumentException("Width of carport is too small.");
        }
        int numberOfPolesWidth = (int) (Math.ceil(widthBetweenPoles/600.0)-1);
        return numberOfPolesWidth;
    }

    public static int calculateNumberOfPolesLength(int length) {
        int lengthBetweenPoles = length - 140;
        if (lengthBetweenPoles < 20) {throw new IllegalArgumentException("Length of carport is too small.");
        }
        int numberOfPolesLength = (int) (Math.ceil(lengthBetweenPoles/340.0)-1);
        return numberOfPolesLength;
    }

    public static int calculateNumberOfPoles(int length, int width) {
        int polesBetweenLength = calculateNumberOfPolesLength(length);
        int polesBetweenWidth = calculateNumberOfPolesWidth(width);

        int numberOfPoles = (2+polesBetweenWidth) * (2+polesBetweenLength);

        return numberOfPoles;
    }

    public static int calculateNumberOfPlates(int width) {
        int numberOfPlates = 2 + calculateNumberOfPolesWidth(width);
        return numberOfPlates;
    }

    public static int calculateNumberOfRafters(int length) {
        int numberOfRafters = (int) (Math.ceil(length/60));
        return numberOfRafters;
    }


    public static double calculateSpanBetweenPlates(int width) {
          int widthBetweenPoles = width - 70;
          double span = widthBetweenPoles/(calculateNumberOfPlates(width)-1.0);
          return span;
    }

    static double[][] spanTable = {
            {120, 145, 170, 195, 220, 245, 295},// dimensions in mm
            {248, 300, 351, 402, 452, 502, 600},// max. span in cm
    };

    public static double calculateDimensions(int width) {
        double span = calculateSpanBetweenPlates(width);
        double dimensions = 0;
        for (int i = 0; i < spanTable[0].length; i++) {
            if (spanTable[1][i] >= span) {
                dimensions = spanTable[0][i];
                break;
            }
        }
        return dimensions;
    }



    /* max length of rafter and plate lumber is 720 cm. If the length is longer than this, we will need two pieces of lumber.
    The length of the rafter is width of carport. The length of the plate is length of carport.
    Assume that the lumber is divided into two(or more) pieces of equal length.
    */
    public int calculateLengthOfLumber(int length) {

        int minlength = length / ((int) Math.ceil(length / 720.0));

        return minlength;
    }

}
