package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import org.abstractica.javacsg.*;

public class Pole3D {
    // 3D geometries for the pole, cutout, and the combination of both
    private Geometry3D pole;
    private Geometry3D cutout;
    private Geometry3D poleAndCutout;

    // List of parts used for creating the pole
    private PartsList partsList;

    public Pole3D(PartsList partsList) {
        this.partsList = partsList;
        this.pole = createPole(partsList);
        this.cutout = createCutout(partsList);
        this.poleAndCutout = createPoleAndCutout(partsList);
    }

    // Method to create a 3D geometry by subtracting the cutout from the pole
    private Geometry3D createPoleAndCutout(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        Geometry3D difference = csg.difference3D(pole, cutout);
        csg.view(difference, 1);
        return difference;
    }

    // Method to create a 3D geometry for the pole
    private Geometry3D createPole(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType pole = partsList.getPole().getLumberType();
        Geometry3D box = csg.box3D( partsList.getLengthOfPole()*10, pole.getWidth(), pole.getThickness(), false);
        return box;
    }

    // Method to create a 3D geometry for the cutout
    private Geometry3D createCutout(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType rafter = partsList.getRafter().getLumberType();
        LumberType pole = partsList.getPole().getLumberType();

        Geometry3D box = csg.box3D(rafter.getWidth() * 2, pole.getThickness() * 1.5, rafter.getThickness(), false);
        Geometry3D translateX = csg.translate3DX(partsList.getLengthOfPole() * 5).transform(box);
        Geometry3D translateZ = csg.translate3DZ(pole.getWidth() - rafter.getThickness()).transform(translateX);
        return translateZ;
    }
}