package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.exceptions.DatabaseException;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class Model3D {

    private final PartsList partsList;
    private static JavaCSG csg = null;

    public Model3D(PartsList partsList) throws DatabaseException {
        this.partsList = partsList;
        if (csg == null) {
            csg = JavaCSGFactory.createDefault();
        }

        this.createModel();
    }

    public void createModel() throws DatabaseException {
        JavaCSG csg = JavaCSGFactory.createDefault();
        Pole3D pole3D = new Pole3D(csg, partsList);
        Plate3D plate3D = new Plate3D(csg, partsList);
        Rafter3D rafter3D = new Rafter3D(csg, partsList);
    }
}