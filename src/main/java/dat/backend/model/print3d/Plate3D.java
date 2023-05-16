package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import org.abstractica.javacsg.*;

public class Plate3D {
    Geometry3D plate;

    PartsList partsList;

    ConnectionPool connectionPool;

    public Plate3D(PartsList partsList) throws DatabaseException {
        this.plate = createPLate(partsList);
        this.partsList = partsList;
    }

    private Geometry3D createPLate(PartsList partsList) throws DatabaseException {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType plate = partsList.getPlate().getLumberType();

        int l = partsList.calculateLengthOfLumber(partsList.getLength(), plate, connectionPool); // TODO: 2021-05-11 Fix this. Somehow connectionpool is null?
        int length = partsList.getLengthOfPlate();
        Geometry3D box = csg.box3D(partsList.getLengthOfPlate()*10, plate.getWidth(), plate.getThickness(), false);
        csg.view(box, 2);
        return box;
    }
}
