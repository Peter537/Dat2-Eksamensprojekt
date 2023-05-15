package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import org.abstractica.javacsg.*;

public class Plate3D {
    Geometry3D plate;

    PartsList partsList;

    public Plate3D(PartsList partsList) throws DatabaseException {
        this.plate = createPLate(partsList);
        this.partsList = partsList;
    }

    private Geometry3D createPLate(PartsList partsList) throws DatabaseException {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType plate = partsList.getPlate().getLumberType();
        Geometry3D box = csg.box3D(partsList.getLengthOfPlate()*10, plate.getWidth(), plate.getThickness(), true);
        csg.view(box, 2);
        return box;
    }
}
