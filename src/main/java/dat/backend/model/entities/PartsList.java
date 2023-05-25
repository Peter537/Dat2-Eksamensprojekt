package dat.backend.model.entities;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.entities.order.CarportOrder;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.services.PartsListCalculator;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class PartsList {

    private final ConnectionPool connectionPool;
    private int height;
    private int length;
    private int width;

    private final Roof roof;
    private int roofArea;

    private Lumber pole; // stolpe
    private int numberOfPoles;

    private Lumber plate; // rem
    private int numberOfPlates;

    private Lumber rafter; // spær
    private int numberOfRafters;

    private float totalPrice;

    public PartsList(int height, int length, int width, Roof roof, ConnectionPool connectionPool) throws DatabaseException {
        this.height = height;
        this.length = length;
        this.width = width;
        this.connectionPool = connectionPool;
        this.roof = roof;
        this.calculate();
    }

    public PartsList(CarportOrder carportOrder, ConnectionPool connectionPool) throws DatabaseException {
        this((int) carportOrder.getMinHeight(), (int) carportOrder.getLength(), (int) carportOrder.getWidth(), carportOrder.getRoof(), connectionPool);
    }

    /**
     * This method will calculate everything needed for the parts list
     *
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    public void calculate() throws DatabaseException {
        this.pole = PartsListCalculator.calculatePole(height, width, connectionPool);
        this.plate = PartsListCalculator.calculatePlate(width, connectionPool);
        this.rafter = PartsListCalculator.calculateRafter(length, width, connectionPool);
        this.roofArea = length / 100 * width / 100;
        this.numberOfPoles = PartsListCalculator.calculateNumberOfPoles(length, width);
        this.numberOfPlates = PartsListCalculator.calculateNumberOfPlates(width) * PartsListCalculator.calculateNumber(length, this.plate.getLumberType(), connectionPool);
        this.numberOfRafters = PartsListCalculator.calculateNumberOfRafters(length) * PartsListCalculator.calculateNumber(width, this.rafter.getLumberType(), connectionPool);
        this.totalPrice = this.calculateTotalPrice();
    }

    public String getFormattedPrice() {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMAN);
        return formatter.format(this.calculateTotalPrice());
    }

    /**
     * This method will calculate the total price of the parts list
     *
     * @return The total price
     */
    public float calculateTotalPrice() {
        return ((pole.getPrice() * numberOfPoles) + (plate.getPrice() * numberOfPlates) + (rafter.getPrice() * numberOfRafters) + (roof.getSquareMeterPrice() * roofArea));
    }

    /**
     * This method will get the length of a Pole
     *
     * @return The length of the pole
     */
    public int getLengthOfPole() {
        return height + 90 + ((int) getRafter().getLumberType().getWidth() / 10);
    }

    /**
     * This method will get the length of a Plate
     *
     * @return The length of the plate
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    public int getLengthOfPlate() throws DatabaseException {
        return PartsListCalculator.calculateLengthOfLumber(length, getPlate().getLumberType(), connectionPool);
    }

    /**
     * This method will get the length of a Rafter
     *
     * @return The length of the rafter
     * @throws DatabaseException if an error occurs while communicating with the database
     */
    public int getLengthOfRafter() throws DatabaseException {
        return PartsListCalculator.calculateLengthOfLumber(width, getRafter().getLumberType(), connectionPool);
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Lumber getPole() {
        return this.pole;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Lumber getPlate() {
        return this.plate;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Lumber getRafter() {
        return this.rafter;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getNumberOfPoles() {
        return this.numberOfPoles;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getNumberOfPlates() {
        return this.numberOfPlates;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getNumberOfRafters() {
        return this.numberOfRafters;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public Roof getRoof() {
        return this.roof;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getRoofArea() {
        return this.roofArea;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getHeight() {
        return this.height;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setHeight(int height) {
        this.height = height;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getLength() {
        return this.length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setLength(int length) {
        this.length = length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getWidth() {
        return this.width;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setWidth(int width) {
        this.width = width;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getTotalPrice() {
        return this.totalPrice;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}