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

    public Rafter3D(JavaCSG csg, PartsList partsList) throws DatabaseException {
        this.csg = csg;
        this.partsList = partsList;
        this.rafter = createRafter();
    }

    private Geometry3D createRafter() throws DatabaseException {
        LumberType rafterType = partsList.getRafter().getLumberType();
        int numberOfRafters = partsList.getNumberOfRafters();
        int length = partsList.getLengthOfRafter() * 10;
        int width = (int)rafterType.getWidth();
        int thickness = (int)rafterType.getThickness();

        Geometry3D box = csg.box3D(length, width, thickness, false);

        // Create cylinder for taps
        Geometry3D hole = csg.cylinder3D((double) width * 0.4, (double) thickness / 2, 50, false);

        // Position holes at each end of the rafter
        Geometry3D tap1 = csg.translate3D((double) -(length - width) /2, 0, (double) thickness ).transform(hole);
        Geometry3D tap2 = csg.translate3D((double) (length - width) /2, 0, (double) thickness).transform(hole);

        // Subtract the holes from the box to create rafter with holes
        box = csg.union3D(box, tap1);
        box = csg.union3D(box, tap2);

        Geometry3D allRafters = box;
        for (int i = 1; i < numberOfRafters; i++) {
            Geometry3D nextRafter = csg.translate3DY(width * 1.5 * i).transform(box);
            allRafters = csg.union3D(allRafters, nextRafter);
        }

        csg.view(allRafters, 3);
        return allRafters;
    }

    public Geometry3D getRafter() {
        return this.rafter;
    }
}
