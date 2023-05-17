package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import dat.backend.model.exceptions.DatabaseException;
import org.abstractica.javacsg.*;

public class Rafter3D {
    private Geometry3D rafter;
    private PartsList partsList;
    private int numberOfRafters;

    public Rafter3D(PartsList partsList) throws DatabaseException {
        this.partsList = partsList;
        this.numberOfRafters = partsList.getNumberOfRafters();
        this.rafter = createRafter(partsList);
    }

    private Geometry3D createRafter(PartsList partsList) throws DatabaseException {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType rafterType = partsList.getRafter().getLumberType();
        Geometry3D box = csg.box3D(partsList.getLengthOfRafter()*10, rafterType.getThickness(), rafterType.getWidth(), false);
        Geometry3D allRafters = box;

        for(int i = 1; i < numberOfRafters; i++){
            Geometry3D nextRafter = csg.translate3DY(rafterType.getThickness()*1.5 * i).transform(box);
            allRafters = csg.union3D(allRafters, nextRafter);
        }
        csg.view(allRafters, 3);
        return allRafters;
    }
}