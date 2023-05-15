package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import org.abstractica.javacsg.*;

public class Pole3D {
    Geometry3D pole;
    Geometry3D cutout;

    PartsList partsList;

    public Pole3D(PartsList partsList) {
        this.partsList = partsList;
        this.pole = createPole(partsList);
        this.cutout = createCutout(partsList);
    }

    private Geometry3D createPole(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType pole = partsList.getPole().getLumberType();
        Geometry3D box = csg.box3D( partsList.getLengthOfPole()*10,pole.getWidth(), pole.getThickness(), false);
        csg.view(box, 1);
        return box;
    }

    private Geometry3D createCutout(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType rafter = partsList.getRafter().getLumberType();
        Geometry3D box = csg.box3D( rafter.getWidth(),rafter.getThickness(),partsList.getPole().getLumberType().getThickness() , false);
        csg.view(box, 4);
        return box;
    }
}
