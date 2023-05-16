package dat.backend.model.print3d;

import dat.backend.model.entities.PartsList;
import dat.backend.model.exceptions.DatabaseException;

public class Model3D {


    void createModel(PartsList partsList) throws DatabaseException {
        Pole3D pole3D = new Pole3D(partsList);
        Plate3D plate3D = new Plate3D(partsList);
        Rafter3D rafter3D = new Rafter3D(partsList);
    }
}