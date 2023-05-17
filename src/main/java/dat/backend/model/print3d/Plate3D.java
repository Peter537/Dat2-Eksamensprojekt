package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import org.abstractica.javacsg.*;

public class Plate3D {
    private Geometry3D plate;
    private PartsList partsList;
    private int numberOfPlates;

    public Plate3D(PartsList partsList) throws DatabaseException {
        this.partsList = partsList;
        this.numberOfPlates = partsList.getNumberOfPlates();
        this.plate = createPlate(partsList);
    }

    private Geometry3D createPlate(PartsList partsList) throws DatabaseException {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType plateType = partsList.getPlate().getLumberType();
        Geometry3D box = csg.box3D(partsList.getLengthOfPlate()*10, plateType.getWidth(), plateType.getThickness(), false);
        Geometry3D allPlates = box;

        for(int i = 1; i < numberOfPlates; i++){
            Geometry3D nextPlate = csg.translate3DY(plateType.getWidth()*1.5 * i).transform(box);
            allPlates = csg.union3D(allPlates, nextPlate);
        }
        csg.view(allPlates, 2);
        return allPlates;
    }
}