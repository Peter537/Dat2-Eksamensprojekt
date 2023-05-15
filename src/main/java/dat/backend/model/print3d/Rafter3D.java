package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import org.abstractica.javacsg.*;

public class Rafter3D {
    Geometry3D rafter;

    PartsList partsList;

    public Rafter3D(PartsList partsList) throws DatabaseException {
        this.rafter = createRafter(partsList);
        this.partsList = partsList;
    }

    private Geometry3D createRafter(PartsList partsList) throws DatabaseException {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType rafter = partsList.getRafter().getLumberType();
        Geometry3D box = csg.box3D( rafter.getWidth(), rafter.getThickness(), partsList.getLengthOfRafter(), true);
        return box;
    }
}