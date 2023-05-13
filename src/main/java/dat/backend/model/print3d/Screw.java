package dat.backend.model.print3D;
import dat.backend.annotation.IgnoreCoverage;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class Screw {
    int length;
    int diameter;
    int numberOfScrews;

    public Screw(int length, int diameter, int numberOfScrews) {
        this.length = length;
        this.diameter = diameter;
        this.numberOfScrews = numberOfScrews;
    }

    public Geometry3D getGeometry() {
        JavaCSG csg = JavaCSGFactory.createDefault();
        Geometry3D cyl = csg.cylinder3D(length, diameter, 90, true);
        //Geometry3D cyl2 =csg.linearExtrude();

        return cyl;
    }

}
