package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.entities.item.LumberType;
import org.abstractica.javacsg.*;

public class Pole3D {
    private Geometry3D pole;
    private Geometry3D cutout;
    private Geometry3D poleAndCutout;
    private PartsList partsList;
    private int numberOfPoles;

    public Pole3D(PartsList partsList) {
        this.partsList = partsList;
        this.numberOfPoles = partsList.getNumberOfPoles();
        this.pole = createPole(partsList);
        this.cutout = createCutout(partsList);
        this.poleAndCutout = createPoleAndCutout(partsList);
    }

    private Geometry3D createPoleAndCutout(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        Geometry3D difference = csg.difference3D(pole, cutout);
        csg.view(difference, 1);
        return difference;
    }

    private Geometry3D createPole(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType pole = partsList.getPole().getLumberType();
        Geometry3D box = csg.box3D(partsList.getLengthOfPole()*10, pole.getWidth(), pole.getThickness(), false);
        Geometry3D allPoles = box;

        for(int i = 1; i < numberOfPoles; i++){
            Geometry3D nextPole = csg.translate3DY(pole.getWidth()*1.5 * i).transform(box);
            allPoles = csg.union3D(allPoles, nextPole);
        }
        return allPoles;
    }

    private Geometry3D createCutout(PartsList partsList) {
        JavaCSG csg = JavaCSGFactory.createDefault();
        LumberType rafter = partsList.getRafter().getLumberType();
        LumberType pole = partsList.getPole().getLumberType();
        Geometry3D box = csg.box3D(rafter.getWidth() * 2, pole.getThickness() * 1.5, rafter.getThickness(), false);
        Geometry3D translateX = csg.translate3DX(partsList.getLengthOfPole() * 5).transform(box);
        Geometry3D translateZ = csg.translate3DZ(pole.getWidth() - rafter.getThickness()).transform(translateX);
        Geometry3D allCutouts = translateZ;

        for(int i = 1; i < numberOfPoles; i++){
            Geometry3D nextCutout = csg.translate3DY(pole.getWidth()*1.5 * i).transform(translateZ);
            allCutouts = csg.union3D(allCutouts, nextCutout);
        }
        return allCutouts;
    }
}