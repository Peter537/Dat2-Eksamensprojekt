package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

public class Rafter3D {

    private final PartsList partsList;
    private final JavaCSG csg;

    private Geometry3D rafter;
    private int numberOfRafters;

    public Rafter3D(JavaCSG csg, PartsList partsList) throws DatabaseException {
        this.csg = csg;
        this.partsList = partsList;
        this.numberOfRafters = partsList.getNumberOfRafters();
        this.rafter = createRafter();
    }

    private Geometry3D createRafter() throws DatabaseException {
        LumberType rafterType = partsList.getRafter().getLumberType();
        Geometry3D box = csg.box3D(partsList.getLengthOfRafter() * 10, rafterType.getThickness(), rafterType.getWidth(), false);
        Geometry3D allRafters = box;
        for (int i = 1; i < numberOfRafters; i++) {
            Geometry3D nextRafter = csg.translate3DY(rafterType.getThickness() * 1.5 * i).transform(box);
            allRafters = csg.union3D(allRafters, nextRafter);
        }

        csg.view(allRafters, 3);
        return allRafters;
    }
}