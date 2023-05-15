package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import org.abstractica.javacsg.*;

public class Pole3D {
    Geometry3D pole;

    PartsList partsList;

    public Pole3D(PartsList partsList) {
        this.pole = createPole(partsList);
        this.partsList = partsList;
    }

    private Geometry3D createPole(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType pole = partsList.getPole().getLumberType();
        Geometry3D box = csg.box3D( partsList.getLengthOfPole()*10,pole.getWidth(), pole.getThickness(), false);
        csg.view(box);
        return box;
    }
}
