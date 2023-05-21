package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

public class Pole3D {

    private final PartsList partsList;
    private final JavaCSG csg;

    private Geometry3D pole;
    private Geometry3D cutout;
    private Geometry3D poleAndCutout;
    private int numberOfPoles;

    public Pole3D(JavaCSG csg, PartsList partsList) {
        this.csg = csg;
        this.partsList = partsList;
        this.numberOfPoles = partsList.getNumberOfPoles();
        this.pole = createPole();
        this.cutout = createCutout();
        this.poleAndCutout = createPoleAndCutout();
    }

    private Geometry3D createPoleAndCutout() {
        Geometry3D difference = csg.difference3D(pole, cutout);
        csg.view(difference, 1);
        return difference;
    }

    private Geometry3D createPole() {
        LumberType pole = partsList.getPole().getLumberType();
        Geometry3D box = csg.box3D(partsList.getLengthOfPole() * 10, pole.getWidth(), pole.getThickness(), false);
        Geometry3D allPoles = box;
        for (int i = 1; i < numberOfPoles; i++) {
            Geometry3D nextPole = csg.translate3DY(pole.getWidth() * 1.5 * i).transform(box);
            allPoles = csg.union3D(allPoles, nextPole);
        }

        return allPoles;
    }

    private Geometry3D createCutout() {
        LumberType rafter = partsList.getRafter().getLumberType();
        LumberType pole = partsList.getPole().getLumberType();
        Geometry3D box = csg.box3D(rafter.getWidth() * 2, pole.getThickness() * 1.5, rafter.getThickness(), false);
        Geometry3D translateX = csg.translate3DX(partsList.getLengthOfPole() * 5).transform(box);
        Geometry3D translateZ = csg.translate3DZ(pole.getWidth() - rafter.getThickness()).transform(translateX);
        Geometry3D allCutouts = translateZ;
        for (int i = 1; i < numberOfPoles; i++) {
            Geometry3D nextCutout = csg.translate3DY(pole.getWidth() * 1.5 * i).transform(translateZ);
            allCutouts = csg.union3D(allCutouts, nextCutout);
        }

        return allCutouts;
    }
}