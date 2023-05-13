package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import org.abstractica.javacsg.*;

public class Plate3D {
    Geometry3D plate;

    PartsList partsList;

    public Plate3D(PartsList partsList) {
        this.plate = createPLate(partsList);
        this.partsList = partsList;
    }

    private Geometry3D createPLate(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType plate = partsList.getPlate().getLumberType();
        Geometry3D box = csg.box3D( plate.getWidth(), plate.getThickness(), partsList.getLengthOfPlate(), true);
        return box;
    }
}
