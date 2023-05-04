package dat.backend.model.entities;

public class PartsList {

    private Lumber pole; // stolpe
    private Lumber plate; // rem
    private Lumber rafter; // spær

    private int numberOfPoles;
    private int numberOfPlates;
    private int numberOfRafters;

    private int priceOfPole;
    private int priceOfPlate;
    private int priceOfRafter;


    public PartsList(Lumber pole, Lumber plate, Lumber rafter, int numberOfPoles, int numberOfPlates, int numberOfRafters, int priceOfPole, int priceOfPlate, int priceOfRafter) {
        this.pole = pole;
        this.plate = plate;
        this.rafter = rafter;
        this.numberOfPoles = numberOfPoles;
        this.numberOfPlates = numberOfPlates;
        this.numberOfRafters = numberOfRafters;
        this.priceOfPole = priceOfPole;
        this.priceOfPlate = priceOfPlate;
        this.priceOfRafter = priceOfRafter;
    }


    // TODO: Given height, length and width. Calculate the number of poles, plates and rafters needed.


//    public PartsList calculatePartsList(int height, int length, int width) {
//        Lumber pole = calculatePole(height);
//        int numberOfPoles = calculateNumberOfPoles(length, width);
//        int numberOfPlates = calculateNumberOfPlates(width);
//        Lumber plate = new Lumber( calculateDimensions(width), priceOfPlate);
//        int numberOfRafters = calculateNumberOfRafters(length);
//        Lumber rafter = new Lumber(60, priceOfRafter); // Assuming all rafters have the same length of 60cm
//
//        return new PartsList(pole, plate, rafter, numberOfPoles, numberOfPlates, numberOfRafters, priceOfPole, priceOfPlate, priceOfRafter);
//    }


    public Lumber calculatePole(int height) {
        // TODO: set the pole length to the height of the carport + 90 cm to be dug into the ground + the width of the plate (same as the rafter).
        return null;
    }

    public Lumber calculateRafter(int length, int width) {
       // new Lumber();
        return null;
    }

    public Lumber calculatePlate(int length, int width) {
        //new Lumber();
        return null;
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

        // TODO: find lumbersizes in database and return the closest match greater than minlength.

        return 0;
    }

}
