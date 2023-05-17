package dat.backend.model.entities;

import dat.backend.annotation.IgnoreCoverage;
import dat.backend.model.entities.item.Lumber;
import dat.backend.model.entities.item.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.exceptions.NotFoundException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.item.RoofFacade;
import dat.backend.model.services.PartsListCalculator;

import java.util.LinkedHashMap;
import java.util.Map;

public class PartsList {

    private final ConnectionPool connectionPool;
    private final int height;
    private final int length;
    private final int width;
    private final Roof roof;
    private final int roofArea;

    private Lumber pole; // stolpe
    private int numberOfPoles;

    private Lumber plate; // rem
    private int numberOfPlates;

    private Lumber rafter; // spær
    private int numberOfRafters;

    private float totalPrice;

    public PartsList(int height, int length, int width, ConnectionPool connectionPool) throws DatabaseException, NotFoundException {
        this.pole = PartsListCalculator.calculatePole(height, width, connectionPool);
        this.plate = PartsListCalculator.calculatePlate(width, connectionPool);
        this.rafter = PartsListCalculator.calculateRafter(length, width, connectionPool);
        this.roof = RoofFacade.getRoofById(1, connectionPool);
        this.roofArea = length / 100 * width / 100;
        this.numberOfPoles = PartsListCalculator.calculateNumberOfPoles(length, width);
        this.numberOfPlates = PartsListCalculator.calculateNumberOfPlates(width) * PartsListCalculator.calculateNumber(length, this.plate.getLumberType(), connectionPool);
        this.numberOfRafters = PartsListCalculator.calculateNumberOfRafters(length) * PartsListCalculator.calculateNumber(width, this.rafter.getLumberType(), connectionPool);
        this.totalPrice = calculateTotalPrice();
        this.height = height;
        this.length = length;
        this.width = width;
        this.connectionPool = connectionPool;
    }

    public Map<Lumber, Integer> getPartsList() {
        Map<Lumber, Integer> map = new LinkedHashMap<>();
        map.put(plate, numberOfPlates);
        map.put(rafter, numberOfRafters);
        map.put(pole, numberOfPoles);
        return map;
    }

    public float calculateTotalPrice() {
        return ((pole.getPrice() * numberOfPoles) + (plate.getPrice() * numberOfPlates) + (rafter.getPrice() * numberOfRafters) + (roof.getSquareMeterPrice() * roofArea));
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
    public int getNumberOfPlates() {
        return this.numberOfPlates;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getNumberOfRafters() {
        return this.numberOfRafters;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public float getTotalPrice() {
        return this.totalPrice;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    public int getLengthOfPole() {
        return height + 90 + ((int) getRafter().getLumberType().getWidth() / 10);
    }

    public int getLengthOfPlate() throws DatabaseException {
        return PartsListCalculator.calculateLengthOfLumber(length, getPlate().getLumberType(), connectionPool);
    }

    public int getLengthOfRafter() throws DatabaseException {
        return PartsListCalculator.calculateLengthOfLumber(width, getRafter().getLumberType(), connectionPool);
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
        return height;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getLength() {
        return length;
    }

    @IgnoreCoverage(reason = "Getter or Setter")
    public int getWidth() {
        return width;
    }

}