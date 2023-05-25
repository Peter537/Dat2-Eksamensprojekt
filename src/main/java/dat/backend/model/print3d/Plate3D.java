package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

public class Plate3D {

    private final PartsList partsList;
    private final JavaCSG csg;

    private Geometry3D plate;
    private int numberOfPlates;
    private int numberOfHoles;

    public Plate3D(JavaCSG csg, PartsList partsList) throws DatabaseException {
        this.csg = csg;
        this.partsList = partsList;
        this.numberOfPlates = partsList.getNumberOfPlates();
        this.numberOfHoles = partsList.getNumberOfRafters();
        this.plate = createPlate();
    }

    private Geometry3D createPlate() throws DatabaseException {
        LumberType plateType = partsList.getPlate().getLumberType();
        Geometry3D box = csg.box3D(partsList.getLengthOfPlate() * 10, plateType.getThickness(), plateType.getWidth(), false);

        // Create cylinder for holes
        Geometry3D hole = csg.cylinder3D((double) plateType.getThickness() *0.5, (double) plateType.getWidth() / 2, 50, false);

        // Subtract the holes from the box to create plate with holes
        for(int i = 0; i < this.numberOfHoles; i++) {
            double holePosition = (double) (partsList.getLengthOfPlate() * 10 - plateType.getThickness()) / 2 - i * (partsList.getLengthOfPlate() * 10 - plateType.getThickness()) / (this.numberOfHoles - 1);
            Geometry3D holeAtPosition = csg.translate3D(holePosition, 0, (double) plateType.getWidth()/2).transform(hole);
            box = csg.difference3D(box, holeAtPosition);
        }

        Geometry3D allPlates = box;
        for (int i = 1; i < numberOfPlates; i++) {
            Geometry3D nextPlate = csg.translate3DY(plateType.getThickness() * 1.5 * i).transform(box);
            allPlates = csg.union3D(allPlates, nextPlate);
        }

        csg.view(allPlates, 2);
        return allPlates;
    }
}