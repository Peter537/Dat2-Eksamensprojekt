package dat.backend.model.persistence.item;

import dat.backend.model.entities.Roof;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.Optional;

public class RoofFacade {
    public static Optional<Roof> createRoof(float squaremeterprice, String type, ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.createRoof(squaremeterprice, type, connectionPool);
    }

    public static Optional<Roof> createRoof(Roof roof, ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.createRoof(roof, connectionPool);
    }

    public static Optional<Roof> getRoofById(int id, ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.getRoofById(id, connectionPool);
    }

    public static Optional<ArrayList<Roof>> getRoofByType(String type, ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.getRoofByType(type, connectionPool);
    }

    public static Optional<ArrayList<Roof>> getAllRoofs(ConnectionPool connectionPool) throws DatabaseException {
        return RoofMapper.getAllRoofs(connectionPool);
    }
}
